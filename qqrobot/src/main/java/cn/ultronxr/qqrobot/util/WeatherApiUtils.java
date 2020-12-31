package cn.ultronxr.qqrobot.util;

import cn.hutool.http.HttpRequest;
import cn.ultronxr.qqrobot.data.GlobalData;

import java.util.HashMap;
import java.util.Map;

public class WeatherApiUtils {

    /**
     * 请求天气API，获取接口响应信息
     *
     * @param paramMap 请求参数Map
     * @return String 响应体内容
     */
    public static String getWeather(Map<String, Object> paramMap) {
        return HttpRequest.get(GlobalData.ALI_WEATHER_API.areaToWeather.getUrl())
                .header("Authorization", GlobalData.ALI_WEATHER_API.authCode.getUrl())
                .form(paramMap)
                .execute()
                .body();
    }

    public static String getWeatherByDefault() {
        Map<String, Object> paramMap = new HashMap<>();
        //paramMap.put("")
        return null;
    }

}
