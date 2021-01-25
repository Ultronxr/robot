package cn.ultronxr.qqrobot.util;

import cn.hutool.http.HttpRequest;
import cn.ultronxr.qqrobot.bean.AliWeatherAPI;
import cn.ultronxr.qqrobot.bean.GlobalData;

import java.util.HashMap;
import java.util.Map;

public class AliWeatherAPIUtils extends AliWeatherAPI {

    /**
     * 请求天气API，获取接口响应信息
     *
     * @param paramMap 请求参数Map
     * @return String 响应体内容
     */
    public static String getWeather(Map<String, Object> paramMap) {
        return HttpRequest.get(API.areaToWeather.getUrl())
                .header("Authorization", AUTH_CODE)
                .form(paramMap)
                .execute()
                .body();
    }

    public static String getWeatherByDefault() {
        Map<String, Object> paramMap = new HashMap<>();
        //paramMap.put("")
        //TODO 2021-1-25 21:03:04 完成天气Utils
        return null;
    }

}
