package cn.ultronxr.qqrobot.eventHandler.eventHandlerImpl;

import cn.hutool.core.util.ReUtil;
import cn.ultronxr.qqrobot.data.GlobalData;
import cn.ultronxr.qqrobot.eventHandler.PingHandler;
import cn.ultronxr.qqrobot.util.PingUtils;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.message.GroupMessageEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
@Slf4j
public class PingHandlerImpl implements PingHandler {

    @Override
    public ListeningStatus pingGroupHandler(GroupMessageEvent groupMsgEvent, String labeledMsg, String unlabeledMsg, String plainMsg) {
        if(plainMsg.contains("http") || plainMsg.contains("https")
                || plainMsg.contains("HTTP") || plainMsg.contains("HTTPS")
                || plainMsg.contains(":")){
            groupMsgEvent.getGroup().sendMessage("请勿携带协议标识和端口号！");
            return ListeningStatus.LISTENING;
        }

        String ipOrDomain = plainMsg.replace("ping", "").trim();
        if(!ReUtil.isMatch(GlobalData.IP_REGEX, ipOrDomain) && !ReUtil.isMatch(GlobalData.DOMAIN_REGEX, ipOrDomain)){
            groupMsgEvent.getGroup().sendMessage("IP或域名错误！");
            return ListeningStatus.LISTENING;
        }

        String res = null;
        try {
            res = PingUtils.pingByCmd(plainMsg.replace("ping", "").trim());
        } catch (IOException e){
            res = "PING命令处理抛出异常！";
            log.error(res);
            e.printStackTrace();
        }
        log.info(res);
        groupMsgEvent.getGroup().sendMessage(res.contains("异常") ? res : res.substring(1, res.lastIndexOf("来自")-1).trim());

        return ListeningStatus.LISTENING;
    }

}
