package cn.ultronxr.qqrobot.service;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * @author Ultronxr
 * @date 2021/05/08 22:19
 *
 * 天气信息Service
 */
public interface WeatherService {

    /**
     * 从天气json中提取出需要的内容，以便发送消息和展示
     *
     * @param weatherJsonRootNode {@code JsonNode} 天气json数据根节点（showapi_res_body）
     * @return {@code String} 提取出的内容拼接而成的天气消息
     */
    String modifyWeatherJson(JsonNode weatherJsonRootNode);

    /**
     * 从天气预报json中提取内容
     *
     * @param weatherJsonForecastNode {@code JsonNode} 天气预报json节点（now、f1~f7）
     * @return {@code String} 提取出的内容拼接而成的天气预报消息
     */
    String modifyForecastWeatherJson(JsonNode weatherJsonForecastNode);

}
