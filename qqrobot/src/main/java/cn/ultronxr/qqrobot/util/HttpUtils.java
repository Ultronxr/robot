package cn.ultronxr.qqrobot.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.zip.GZIPInputStream;

/**
 * HTTP请求通用类封装
 */
public class HttpUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * 天气Request Header Map
     */
    private static final HashMap<String, String> WEATHER_REQ_HEADER_MAP = new HashMap<>(10);

    static {
        //WEATHER_REQ_HEADER_MAP.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        //WEATHER_REQ_HEADER_MAP.put("Accept-Encoding", "gzip, deflate");
        //WEATHER_REQ_HEADER_MAP.put("Accept-Language", "zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7");
        //WEATHER_REQ_HEADER_MAP.put("Cache-Control", "max-age=0");
        //WEATHER_REQ_HEADER_MAP.put("Connection", "keep-alive");
        WEATHER_REQ_HEADER_MAP.put("User-Agent", "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.81 Safari/537.36");
    }

    /**
     * GET方式请求
     * @param url           不带参数的URL
     * @param reqHeaderMap  请求头Map（Request Header）
     * @param paramMap      查询参数Map（Parameter）
     * @return String       请求结果，字符串形式返回
     * @throws IOException  网络请求过程中出现的异常
     */
    //public static String httpGet(String url, HashMap<String, String> reqHeaderMap, HashMap<String, String> paramMap) throws IOException {
    //    //组装请求参数
    //    StringBuilder paramStr = new StringBuilder();
    //    if(Objects.nonNull(paramMap)){
    //        paramStr.append("?");
    //        paramMap.keySet().forEach(key -> paramStr.append(key).append("=").append(paramMap.get(key)));
    //    }
    //
    //    //设置请求头和请求参数，建立连接
    //    URL urlObj = new URL(url + paramStr.toString());
    //    LOGGER.info("HTTP REQUEST [GET]: " + url + paramStr.toString());
    //    HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
    //    reqHeaderMap.keySet().forEach(key -> conn.setRequestProperty(key, reqHeaderMap.get(key)));
    //    conn.setRequestMethod("GET");
    //    conn.connect();
    //
    //    //获取响应头和响应输入流，同时判断是否有gzip压缩
    //    Map<String, List<String>> respHeaderMap = conn.getHeaderFields();
    //    InputStream inStream = conn.getInputStream();
    //    String res = null;
    //    if(respHeaderMap.containsKey("Content-Encoding") && respHeaderMap.get("Content-Encoding").contains("gzip")){
    //        res = unGzip(inStream);
    //    } else {
    //        InputStreamReader inStreamReader = new InputStreamReader(inStream, StandardCharsets.UTF_8);
    //        BufferedReader respReader = new BufferedReader(inStreamReader);
    //        StringBuilder respStr = new StringBuilder();
    //        String tmpStr = null;
    //        while (Objects.nonNull(tmpStr = respReader.readLine())){
    //            respStr.append(tmpStr);
    //        }
    //        res = respStr.toString();
    //
    //        respReader.close();
    //        inStreamReader.close();
    //    }
    //
    //    //资源释放
    //    inStream.close();
    //    conn.disconnect();
    //
    //    return res;
    //}
    //
    ///**
    // * 如果请求返回经过gzip压缩，则把返回内容解压缩
    // * @param inStream  HTTP请求之后的响应输入流
    // * @return String   解压缩后的内容
    // */
    //private static String unGzip(InputStream inStream) throws IOException {
    //    if (Objects.isNull(inStream)) {
    //        return "";
    //    }
    //    GZIPInputStream gzipInStream = null;
    //    ByteArrayOutputStream byteArrOutStream = null;
    //    byte[] buffer = new byte[9999];
    //
    //    gzipInStream = new GZIPInputStream(inStream);
    //    byteArrOutStream = new ByteArrayOutputStream();
    //    int n;
    //    while ((n = gzipInStream.read(buffer)) >= 0) {
    //        byteArrOutStream.write(buffer, 0, n);
    //    }
    //
    //    byteArrOutStream.close();
    //    gzipInStream.close();
    //
    //    return new String(buffer, StandardCharsets.UTF_8).trim();
    //}
    //
    ///**
    // * GET方式请求获取天气信息
    // * @param paramMap       查询参数Map（Parameter）
    // * @return LinkedHashMap 有序的天气详情Map
    // * @throws IOException   网络请求过程中出现的异常
    // *
    // * {
    // * 	"data": {
    // * 		"yesterday": {
    // * 			"date": "2日星期三",
    // * 			"high": "高温 11℃",
    // * 			"fx": "北风",
    // * 			"low": "低温 7℃",
    // * 			"fl": "<![CDATA[2级]]>",
    // * 			"type": "阴"
    // *      },
    // * 		"city": "杭州",
    // * 		"forecast": [
    // *            {
    // * 				"date": "3日星期四",
    // * 				"high": "高温 9℃",
    // * 				"fengli": "<![CDATA[3级]]>",
    // * 				"low": "低温 4℃",
    // * 				"fengxiang": "北风",
    // * 				"type": "阴"
    // *            },
    // *            {
    // * 				"date": "4日星期五",
    // * 				"high": "高温 9℃",
    // * 				"fengli": "<![CDATA[2级]]>",
    // * 				"low": "低温 4℃",
    // * 				"fengxiang": "北风",
    // * 				"type": "多云"
    // *            },
    // *            {
    // * 				"date": "5日星期六",
    // * 				"high": "高温 12℃",
    // * 				"fengli": "<![CDATA[2级]]>",
    // * 				"low": "低温 5℃",
    // * 				"fengxiang": "东北风",
    // * 				"type": "阴"
    // *            },
    // *            {
    // * 				"date": "6日星期天",
    // * 				"high": "高温 15℃",
    // * 				"fengli": "<![CDATA[1级]]>",
    // * 				"low": "低温 8℃",
    // * 				"fengxiang": "东风",
    // * 				"type": "多云"
    // *            },
    // *            {
    // * 				"date": "7日星期一",
    // * 				"high": "高温 14℃",
    // * 				"fengli": "<![CDATA[2级]]>",
    // * 				"low": "低温 9℃",
    // * 				"fengxiang": "北风",
    // * 				"type": "阴"
    // *            }
    // * 		],
    // * 		"ganmao": "感冒多发期，适当减少外出频率，适量补充水分，适当增减衣物。",
    // * 		"wendu": "6"
    // * 	},
    // * 	"status": 1000,
    // * 	"desc": "OK"
    // * }
    // */
    //public static LinkedHashMap<String, Object> httpGetWeather(HashMap<String, String> paramMap) throws IOException {
    //    String res = httpGet("http://wthrcdn.etouch.cn/weather_mini", WEATHER_REQ_HEADER_MAP, paramMap);
    //    LOGGER.info("HTTP RESPONSE [GET]: "+res);
    //
    //    LinkedHashMap<String, Object> weatherMap = new LinkedHashMap<>();
    //
    //    //解析json
    //    ObjectMapper objMapper = new ObjectMapper();
    //    JsonNode rootNode = objMapper.readTree(res);
    //    if(!"1000".equals(rootNode.path("status").asText())
    //            || !"OK".equals(rootNode.path("desc").asText())){
    //        weatherMap.put("状态", "无数据");
    //        return weatherMap;
    //    }
    //
    //    JsonNode dataNode = rootNode.path("data");
    //    weatherMap.put("状态", "数据正常");
    //    weatherMap.put("城市", dataNode.path("city").asText());
    //    weatherMap.put("当前温度", dataNode.path("wendu").asText() + "℃");
    //
    //    weatherMap.put("今天", modifyForecast(dataNode.path("forecast").path(0)));
    //    weatherMap.put("明天", modifyForecast(dataNode.path("forecast").path(1)));
    //    weatherMap.put("后天", modifyForecast(dataNode.path("forecast").path(2)));
    //    weatherMap.put("大后天", modifyForecast(dataNode.path("forecast").path(3)));
    //
    //    //weatherMap.keySet().forEach(key -> LOGGER.info(key + " " + weatherMap.get(key)));
    //    return weatherMap;
    //}
    //
    //private static String modifyForecast(JsonNode forecastNode){
    //    StringBuilder strBuilder = new StringBuilder("\n");
    //    String lowTemp = forecastNode.path("low").asText().replace("低温 ", ""),
    //            highTemp = forecastNode.path("high").asText().replace("高温 ", "");
    //    strBuilder.append("天气：").append(forecastNode.path("type").asText()).append("；")
    //            .append("温度：").append(lowTemp).append("~").append(highTemp).append("；")
    //            .append("风力：").append(forecastNode.path("fengli").asText().replace("<![CDATA[", "").replace("]]>", ""));
    //    return strBuilder.toString();
    //}

}
