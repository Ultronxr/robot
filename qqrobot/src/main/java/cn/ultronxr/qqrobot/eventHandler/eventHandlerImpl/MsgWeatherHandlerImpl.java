package cn.ultronxr.qqrobot.eventHandler.eventHandlerImpl;

import cn.ultronxr.qqrobot.bean.GlobalData;
import cn.ultronxr.qqrobot.eventHandler.MsgWeatherHandler;
import cn.ultronxr.qqrobot.util.AliWeatherAPIUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class MsgWeatherHandlerImpl extends GlobalData implements MsgWeatherHandler {

    private final ObjectMapper jsonObjMapper = new ObjectMapper();


    @Override
    public void groupWeatherHandler(GroupMessageEvent groupMsgEvent, String msgCode, String msgContent, String msgPlain) {
        String area = msgPlain.replace("天气", "").trim();
        area = "".equals(area) ? "杭州" : area;

        JsonNode rootNode = null;
        try {
            String weatherJson = AliWeatherAPIUtils.getWeatherByArea(area);
            rootNode = jsonObjMapper.readTree(weatherJson).path("showapi_res_body");
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
            log.warn("[function] 天气Handler处理异常！");
            groupMsgEvent.getSubject().sendMessage("天气Handler处理异常！");
            return;
        }

        String resStr = null;
        if(! "0".equals(rootNode.path("ret_code").asText())){
            // 数据错误，返回说明信息
            resStr = rootNode.path("remark").asText();
        } else {
            // 数据正确，解析数据
            resStr = modifyWeatherJson(rootNode);
        }
        groupMsgEvent.getSubject().sendMessage(resStr);
        log.info("[message-send] " + resStr);
    }

    /**
     * 从天气json中提取出需要的内容，以便发送消息和展示
     *
     * @param weatherJsonRootNode {@code JsonNode} 天气json数据根节点（showapi_res_body）
     * @return {@code String} 提取出的内容拼接而成的天气消息
     */
    private String modifyWeatherJson(JsonNode weatherJsonRootNode){
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

    /**
     * 从天气预报json中提取内容
     *
     * @param weatherJsonForecastNode {@code JsonNode} 天气预报json节点（now、f1~f7）
     * @return {@code String} 提取出的内容拼接而成的天气预报消息
     */
    private String modifyForecastWeatherJson(JsonNode weatherJsonForecastNode){
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
