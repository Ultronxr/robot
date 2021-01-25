package cn.ultronxr.qqrobot.eventHandler.eventHandlerImpl;

import cn.ultronxr.qqrobot.bean.GlobalData;
import cn.ultronxr.qqrobot.eventHandler.WeatherHandler;
import cn.ultronxr.qqrobot.util.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.message.GroupMessageEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;


@Component
@Slf4j
public class WeatherHandlerImpl extends GlobalData implements WeatherHandler {

    @Override
    public ListeningStatus weatherGroupHandler(GroupMessageEvent groupMsgEvent, String labeledMsg, String unlabeledMsg, String plainMsg) {
        //HashMap<String, String> paramMap = new HashMap<>(2);
        //StringBuilder weatherStr = new StringBuilder();

        //paramMap.put("city", "天气".equals(plainMsg) ? "杭州" : plainMsg.replace("天气", "").trim());

        //try {
        //    LinkedHashMap<String, Object> weatherMap = HttpUtils.httpGetWeather(paramMap);
        //    if("无数据".equals(weatherMap.get("状态"))){
        //        weatherStr.append("●状态：无数据\n●城市名称无效");
        //        log.warn("天气查询中的城市名称无效！");
        //    } else {
        //        weatherMap.keySet().forEach(key -> {
        //            weatherStr.append("●").append(key).append("：").append(weatherMap.get(key)).append("\n");
        //        });
        //        //log.info(weatherStr.toString());
        //    }
        //} catch (IOException e){
        //    weatherStr.append("●状态：无数据");
        //    log.warn("天气查询抛出异常！");
        //    e.printStackTrace();
        //}
        //groupMsgEvent.getGroup().sendMessage(weatherStr.toString());

        return ListeningStatus.LISTENING;
    }

}
