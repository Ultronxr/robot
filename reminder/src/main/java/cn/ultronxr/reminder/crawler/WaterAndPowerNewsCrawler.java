package cn.ultronxr.reminder.crawler;

import cn.hutool.http.HttpRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class WaterAndPowerNewsCrawler {

    private static final String newsUrl = "http://www.lxnews.cn";
    private static final String ajaxUrl = "http://d.cztvcloud.com/search?d=www.lxnews.cn&sort=1&p=1&size=1&q=";
    public static final String waterKey = "储水准备";
    public static final String powerKey = "停电通知";


    public static String crawlerHttp(String keyword) {
        if(StringUtils.isEmpty(keyword)){
            return "";
        }

        return HttpRequest.get(ajaxUrl + keyword)
                .header("Accept", "application/json, text/javascript, */*; q=0.01")
                .header("Accept-Language", "zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7")
                .header("Host", "d.cztvcloud.com")
                .header("Origin", "http://www.lxnews.cn")
                .header("Referer", "http://www.lxnews.cn/")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.81 Safari/537.36")
                .execute()
                .body();
    }

    public static Map<String, String> formatResponse(String respStr) throws JsonProcessingException {
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
        resMap.put("url", newsUrl + contentArrNode.get(0).path("url").asText());
        resMap.put("mobileUrl", contentArrNode.get(0).path("referer_url").asText());
        //timestamp指的是新闻发布时间！
        resMap.put("timestamp", contentArrNode.get(0).path("publish_at").asText());
        String prettyContentStr = contentArrNode.get(0).path("content").asText().replaceAll("<[.[^>]]*>","");
        resMap.put("content", prettyContentStr);

        return resMap;
    }

    public enum RemindCode {
        BadResponse(-1, "-1", "爬取到的新闻内容返回不正确"),
        DoNotRemind(0, "0", "新闻内容正确但不包含居住地区，或停水/停电时间不在未来一天内"),
        DoRemind(1, "1", "新闻内容正确且包含居住地区，且停水/停电时间在未来一天内");

        private int intCode;
        private String strCode;
        private String msg;

        RemindCode(int intCode, String strCode, String msg) {
            this.intCode = intCode;
            this.strCode= strCode;
            this.msg = msg;
        }

        public int getIntCode() {
            return intCode;
        }

        public String getStrCode() {
            return strCode;
        }

        public String getMsg() {
            return msg;
        }
    }

    private static Map<String, String> putRemindCode(Map<String, String> resMap){
        if(!resMap.containsKey("code") || !resMap.containsKey("msg")){
            return resMap;
        }

        if(!"200".equals(resMap.get("code")) || !"OK".equals(resMap.get("msg"))){
            resMap.put("remindCode", RemindCode.BadResponse.getStrCode());
            return resMap;
        }
        String content = resMap.get("content");
        long timestampNow = System.currentTimeMillis() / 1000,
                timestampGap = timestampNow - Long.parseLong(resMap.get("timestamp"));
        if((content.contains("马涧") || content.contains("溪源"))
                && 0 < timestampGap && timestampGap < 24*60*60){
            resMap.put("remindCode", RemindCode.DoRemind.getStrCode());
        } else {
            resMap.put("remindCode", RemindCode.DoNotRemind.getStrCode());
        }

        return resMap;
    }

    public static Map<String, String> waterReminder() throws JsonProcessingException {
        return putRemindCode(formatResponse(crawlerHttp(waterKey)));
    }

    public static Map<String, String> powerReminder() throws JsonProcessingException {
        return putRemindCode(formatResponse(crawlerHttp(powerKey)));
    }

}
