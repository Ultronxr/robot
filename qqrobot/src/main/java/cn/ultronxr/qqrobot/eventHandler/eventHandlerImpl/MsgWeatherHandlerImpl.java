package cn.ultronxr.qqrobot.eventHandler.eventHandlerImpl;

import cn.ultronxr.qqrobot.bean.GlobalData;
import cn.ultronxr.qqrobot.eventHandler.MsgWeatherHandler;
import cn.ultronxr.qqrobot.service.WeatherService;
import cn.ultronxr.qqrobot.util.AliWeatherAPIUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class MsgWeatherHandlerImpl extends GlobalData implements MsgWeatherHandler {

    private final ObjectMapper jsonObjMapper = new ObjectMapper();

    @Autowired
    private WeatherService weatherService;


    @Override
    public void groupWeatherHandler(GroupMessageEvent groupMsgEvent, String msgPlain) {
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
            resStr = weatherService.modifyWeatherJson(rootNode);
        }
        groupMsgEvent.getSubject().sendMessage(resStr);
        log.info("[message-send] " + resStr);
    }

}
