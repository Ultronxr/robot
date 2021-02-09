package cn.ultronxr.qqrobot.eventHandler.eventHandlerImpl;

import cn.hutool.core.util.ReUtil;
import cn.ultronxr.qqrobot.bean.GlobalData;
import cn.ultronxr.qqrobot.eventHandler.PingHandler;
import cn.ultronxr.qqrobot.util.PingUtils;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
@Slf4j
public class PingHandlerImpl extends GlobalData implements PingHandler {

    @Override
    public ListeningStatus groupPingHandler(GroupMessageEvent groupMsgEvent, String msgCode, String msgContent, String msgPlain) {
        String address = msgPlain.replace("ping", "").trim();
        String msg = null;

        if(address.startsWith("http") || address.startsWith("https")
                || address.startsWith("HTTP") || address.startsWith("HTTPS")
                || address.contains(":")){
            log.info("[function] ping命令结果：" + (msg = "请勿携带协议标识和端口号！"));
            groupMsgEvent.getGroup().sendMessage(msg);
            log.info("[message-send] "+msg);
            return ListeningStatus.LISTENING;
        }

        if(!ReUtil.isMatch(Regex.IP, address) && !ReUtil.isMatch(Regex.DOMAIN, address)){
            log.info("[function] ping命令结果：" + (msg = "IP或域名错误！"));
            groupMsgEvent.getGroup().sendMessage(msg);
            log.info("[message-send] "+msg);
            return ListeningStatus.LISTENING;
        }

        if(ReUtil.isMatch(Regex.IP_INTRANET, address) || ReUtil.isMatch(Regex.DOMAIN_INTRANET, address)){
            log.info("[function] ping命令结果：" + (msg = "禁止ping内网！"));
            groupMsgEvent.getGroup().sendMessage(msg);
            log.info("[message-send] "+msg);
            return ListeningStatus.LISTENING;
        }

        String res = null;
        try {
            res = PingUtils.pingByCmd(msgPlain.replace("ping", "").trim());
        } catch (IOException e){
            log.error("[function] ping命令结果：" + (res = "ping命令处理抛出异常！"));
            e.printStackTrace();
        }
        log.info("[function]" + res);
        groupMsgEvent.getSubject().sendMessage(res.contains("异常") ? res : res.substring(1, res.lastIndexOf("来自")-1).trim());

        return ListeningStatus.LISTENING;
    }

}
