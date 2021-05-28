package cn.ultronxr.qqrobot.eventHandler.eventHandlerImpl;

import cn.ultronxr.qqrobot.bean.BotCmd;
import cn.ultronxr.qqrobot.bean.GlobalData;
import cn.ultronxr.qqrobot.eventHandler.MsgWeatherHandler;
import cn.ultronxr.qqrobot.service.WeatherService;
import cn.ultronxr.qqrobot.util.CommonCliUtils;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.apache.commons.cli.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Ultronxr
 * @date 2020/12/31 15:03
 */
@NoArgsConstructor
@Component
@Slf4j
public class MsgWeatherHandlerImpl extends GlobalData implements MsgWeatherHandler {

    @Autowired
    private WeatherService weatherService;


    @Override
    public void groupWeatherHandler(BotCmd botCmd, CommandLine cmdLine, GroupMessageEvent groupMsgEvent, String msgPlain) {
        if(cmdLine.hasOption("help")) {
            // 优先判断 --help 选项
            CommonCliUtils.defaultOptionHelper(groupMsgEvent, botCmd);
            return;
        }

        String area = null;
        if(cmdLine.getOptions().length == 0) {
            // 无任何选项时，地区默认设置为杭州
            area = "杭州";
        } else if(cmdLine.hasOption("a")) {
            // 有 -a 选项时，获取地区名称
            area = cmdLine.getOptionValue("a");
        } else {
            CommonCliUtils.defaultOptionExceptionHandler(groupMsgEvent);
            return;
        }

        String resStr = weatherService.processWeatherApiAndModifyResult(area);
        groupMsgEvent.getSubject().sendMessage(resStr);
        log.info("[Msg-Send] " + resStr);
    }

}
