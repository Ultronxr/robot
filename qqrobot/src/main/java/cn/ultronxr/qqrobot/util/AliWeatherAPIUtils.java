package cn.ultronxr.qqrobot.util;

import cn.hutool.http.HttpRequest;
import cn.ultronxr.qqrobot.bean.AliWeatherAPI;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class AliWeatherAPIUtils extends AliWeatherAPI {

    /**
     * 请求天气API，获取接口响应信息
     *
     * @param paramMap 请求参数Map
     * @return String 响应体内容
     */
    public static String requestWeather(Map<String, Object> paramMap) {
        return HttpRequest.get(API.areaToWeather.getUrl())
                .header("Authorization", AUTH_CODE)
                .form(paramMap)
                .execute()
                .body();
    }

    public static String getWeatherByArea(String area) {
        Map<String, Object> paramMap = new HashMap<>(10);
        paramMap.put("area", area);
        paramMap.put("areaid", "");
        paramMap.put("need3HourForcast", "0");
        paramMap.put("needAlarm", "0");
        paramMap.put("needHourData", "0");
        paramMap.put("needIndex", "0");
        paramMap.put("needMoreDay", "0");

        return requestWeather(paramMap);
    }

    public static void main(String[] args) {
        System.out.println(getWeatherByArea("兰溪"));
    }

}
