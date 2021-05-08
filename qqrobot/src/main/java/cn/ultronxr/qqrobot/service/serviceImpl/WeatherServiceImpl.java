package cn.ultronxr.qqrobot.service.serviceImpl;

import cn.ultronxr.qqrobot.service.WeatherService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Ultronxr
 * @date 2021/05/08 22:19
 */
@Service
@Slf4j
public class WeatherServiceImpl implements WeatherService {


    @Override
    public String modifyWeatherJson(JsonNode weatherJsonRootNode){
        StringBuilder strBuilder = new StringBuilder();

        // cityInfo-城市信息、now-当前天气、f1-今日天气、f2-明天天气、f3-后天天气
        JsonNode cityInfo = weatherJsonRootNode.path("cityInfo"),
                now = weatherJsonRootNode.path("now"),
                f1 = weatherJsonRootNode.path("f1"),
                f2 = weatherJsonRootNode.path("f2"),
                f3 = weatherJsonRootNode.path("f3");

        strBuilder.append("●地区\n  ")
                .append(cityInfo.path("c9").asText()).append(" - ")
                .append(cityInfo.path("c7").asText()).append(" - ")
                .append(cityInfo.path("c3").asText()).append("\n");
        strBuilder.append("●经纬海拔\n  ")
                .append(cityInfo.path("longitude").asText()).append(" - ")
                .append(cityInfo.path("latitude").asText()).append(" - ")
                .append(cityInfo.path("c15").asText()).append("\n");
        strBuilder.append("●今天天气\n").append(modifyForecastWeatherJson(f1))
                .append("●明天天气\n").append(modifyForecastWeatherJson(f2))
                .append("●后天天气\n").append(modifyForecastWeatherJson(f3));

        return strBuilder.toString();
    }

    @Override
    public String modifyForecastWeatherJson(JsonNode weatherJsonForecastNode){
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("  降水概率：").append(weatherJsonForecastNode.path("jiangshui").asText()).append("\n")
                .append("  白天天气：").append(weatherJsonForecastNode.path("day_weather").asText()).append("\n")
                .append("  白天气温：").append(weatherJsonForecastNode.path("day_air_temperature").asText()).append(" ℃\n")
                .append("  白天风力：").append(weatherJsonForecastNode.path("day_wind_power").asText()).append("\n")
                .append("  夜间天气：").append(weatherJsonForecastNode.path("night_weather").asText()).append("\n")
                .append("  夜间气温：").append(weatherJsonForecastNode.path("night_air_temperature").asText()).append(" ℃\n")
                .append("  夜间风力：").append(weatherJsonForecastNode.path("night_wind_power").asText()).append("\n")
                .append("  日出日落：").append(weatherJsonForecastNode.path("sun_begin_end").asText()).append("\n");
        return strBuilder.toString();
    }

}
