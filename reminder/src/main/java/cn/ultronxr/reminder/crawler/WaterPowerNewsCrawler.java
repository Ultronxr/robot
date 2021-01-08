package cn.ultronxr.reminder.crawler;

import cn.hutool.http.HttpRequest;
import cn.ultronxr.reminder.bean.GlobalData;
import cn.ultronxr.reminder.bean.RemindCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;


@Slf4j
public class WaterPowerNewsCrawler extends GlobalData {

    /** 网页新闻URL */
    private static final String NEWS_URL = ResourceBundles.WATER_POWER_REMINDER.getString("url.newsUrl");

    /** ajax请求获取json数据URL */
    private static final String AJAX_URL = ResourceBundles.WATER_POWER_REMINDER.getString("url.ajaxUrl");

    /** ajax请求获取json数据HOST */
    private static final String AJAX_HOST = ResourceBundles.WATER_POWER_REMINDER.getString("url.ajaxHost");

    /** 停水和停电的搜索关键词 */
    private static final String WATER_KEY = ResourceBundles.getChineseResource("keyword.waterKey");
    private static final String POWER_KEY = ResourceBundles.getChineseResource("keyword.powerKey");

    /** 新闻content中需要提醒的关键词 */
    private static final String[] REMIND_KEY = ResourceBundles.getChineseResource("keyword.remindKey").split(" ");

    /**
     * 确保提醒的时效性，单位为秒（LOW <= timestamp <= high）
     * 当前设置时效：未来12小时内
     */
    private static final long TIMESTAMP_LOW = Long.parseLong(ResourceBundles.WATER_POWER_REMINDER.getString("timestamp.low"));
    private static final long TIMESTAMP_HIGH = Long.parseLong(ResourceBundles.WATER_POWER_REMINDER.getString("timestamp.high"));


    /**
     * 请求数据接口获取返回结果
     *
     * @param keyword
     *          停水和停电的搜索关键词
     *
     * @return
     *          接口返回的结果，json字符串{@code String}
     */
    private static String crawlerHttp(String keyword) {
        if(StringUtils.isEmpty(keyword)){
            return "";
        }

        return HttpRequest.get(AJAX_URL + keyword)
                .header("Accept", "application/json, text/javascript, */*; q=0.01")
                .header("Accept-Language", "zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7")
                .header("Host", AJAX_HOST)
                .header("Origin", NEWS_URL)
                .header("Referer", NEWS_URL)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.81 Safari/537.36")
                .execute()
                .body();
    }

    /**
     * 格式化接口返回结果的json字符串，以便后续处理
     *
     * @param respStr
     *          {@code crawlerHttp()}返回的json字符串
     *
     * @return
     *          一个{@code Map<String, String>}，其中包含了：
     *          标识符 code、标识信息 msg、网页完整URL url、URL最后一层路径 shortUrl、手机端URL mobileUrl、发布时间戳 timestamp、新闻内容 content
     *
     * @throws JsonProcessingException
     *          json序列化失败抛出的异常信息
     */
    private static Map<String, String> formatResponse(String respStr) throws JsonProcessingException {
        Map<String, String> resMap = new HashMap<>(10);
        resMap.put("code", "0");
        resMap.put("msg", "No Data");

        if(StringUtils.isEmpty(respStr)){
            return resMap;
        }

        ObjectMapper objMapper = new ObjectMapper();
        JsonNode rootNode = objMapper.readTree(respStr);
        ArrayNode contentArrNode = (ArrayNode) rootNode.path("data").path("rs");

        resMap.put("code", rootNode.path("code").asText());
        resMap.put("msg", rootNode.path("msg").asText());
        resMap.put("url", NEWS_URL + contentArrNode.get(0).path("url").asText());
        resMap.put("shortUrl", contentArrNode.get(0).path("url").asText());
        resMap.put("mobileUrl", contentArrNode.get(0).path("referer_url").asText());
        //timestamp指的是新闻发布时间！
        resMap.put("timestamp", contentArrNode.get(0).path("publish_at").asText());
        String prettyContentStr = contentArrNode.get(0).path("content").asText().replaceAll("<[.[^>]]*>","");
        resMap.put("content", prettyContentStr);

        return resMap;
    }

    /**
     * 判断此条新闻通知是否需要提醒（地区关键词、时效性），
     * 向格式化后的{@code Map<String, String>}中插入remindCode键值
     *
     * @param resMap
     *          {@code formatResponse()}方法返回的格式化后的{@code Map<String, String>}
     *
     * @return
     *          添加了remindCode键值的新{@code Map<String, String>}
     */
    private static Map<String, String> putRemindCode(Map<String, String> resMap){
        if(!resMap.containsKey("code") || !resMap.containsKey("msg")){
            return resMap;
        }
        if(!"200".equals(resMap.get("code")) || !"OK".equals(resMap.get("msg"))){
            resMap.put("remindCode", RemindCode.BadResponse.getStrCode());
            return resMap;
        }

        //判断新闻内容时效性（以发布的时间为准，停水/停电一般在发布后的一到两天内）
        String content = resMap.get("content");
        long timestampNow = System.currentTimeMillis() / 1000,
                timestampGap = timestampNow - Long.parseLong(resMap.get("timestamp"));
        for (String remindKey : REMIND_KEY) {
            if(content.contains(remindKey)
                    && TIMESTAMP_LOW <= timestampGap && timestampGap <= TIMESTAMP_HIGH){
                resMap.put("remindCode", RemindCode.DoRemind.getStrCode());
                break;
            }
        }
        if(!resMap.containsKey("remindCode")){
            resMap.put("remindCode", RemindCode.DoNotRemind.getStrCode());
        }

        return resMap;
    }

    /**
     * 停水提醒器
     * 外部直接调用该方法即可
     *
     * @return
     *          {@code putRemindCode()}方法处理后的最终结果{@code Map<String, String>}，其中包含了：
     *          标识符 code、标识信息 msg、网页完整URL url、URL最后一层路径 shortUrl、手机端URL mobileUrl、发布时间戳 timestamp、新闻内容 content、
     *          是否需要提醒标识符 remindCode
     *
     * @throws JsonProcessingException
     *          json序列化失败抛出的异常信息
     */
    public static Map<String, String> waterReminder() throws JsonProcessingException {
        return putRemindCode(formatResponse(crawlerHttp(WATER_KEY)));
    }

    /**
     * 停电提醒器
     * 外部直接调用该方法即可
     *
     * @return
     *          {@code putRemindCode()}方法处理后的最终结果{@code Map<String, String>}，其中包含了：
     *          标识符 code、标识信息 msg、网页完整URL url、URL最后一层路径 shortUrl、手机端URL mobileUrl、发布时间戳 timestamp、新闻内容 content、
     *          是否需要提醒标识符 remindCode
     *
     * @throws JsonProcessingException
     *          json序列化失败抛出的异常信息
     */
    public static Map<String, String> powerReminder() throws JsonProcessingException {
        return putRemindCode(formatResponse(crawlerHttp(POWER_KEY)));
    }

}
