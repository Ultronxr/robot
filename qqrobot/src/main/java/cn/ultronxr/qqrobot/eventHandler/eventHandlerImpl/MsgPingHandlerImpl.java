package cn.ultronxr.qqrobot.eventHandler.eventHandlerImpl;

import cn.hutool.core.lang.PatternPool;
import cn.hutool.core.util.ReUtil;
import cn.ultronxr.qqrobot.bean.GlobalData;
import cn.ultronxr.qqrobot.eventHandler.MsgPingHandler;
import cn.ultronxr.qqrobot.util.PingUtils;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Component
@Slf4j
public class MsgPingHandlerImpl extends GlobalData implements MsgPingHandler {

    @Override
    public void groupPingHandler(GroupMessageEvent groupMsgEvent, String msgCode, String msgContent, String msgPlain) {
        List<String> fixedAddrList = fixAddrMsg(msgPlain);
        String pingRes = "";

        log.info("[function] ping命令fix合法标记：" + fixedAddrList.get(0));
        log.info("[function] ping命令fix结果：" + fixedAddrList.get(1));

        if("1".equals(fixedAddrList.get(0))){
            try {
                pingRes = PingUtils.pingByRuntime(fixedAddrList.get(1));
                log.info("[function] ping命令执行结果：" + pingRes);
            } catch (IOException ex) {
                ex.printStackTrace();
                pingRes = "ping命令抛出异常！";
                log.warn("[function] ping命令抛出异常！");
            } finally {
                groupMsgEvent.getSubject().sendMessage(pingRes);
                log.info("[message-send] " + pingRes);
            }
        } else {
            groupMsgEvent.getSubject().sendMessage(fixedAddrList.get(1));
            log.info("[message-send] " + fixedAddrList.get(1));
        }
    }

    /**
     * 修正传入的address参数，判断是否合法
     * 返回合法标记、address结果（合法情况）/错误信息（非法情况）
     *
     * @param addrMsg 包含传入的IP地址或URL链接的消息
     * @return {@code List<String>} 返回合法标记、address结果/错误信息
     *                              合法：{"1", "address结果"}
     *                              非法：{"0", "错误信息"}
     */
    private List<String> fixAddrMsg(String addrMsg){
        List<String> resList = new ArrayList<>(2);

        // 分别获取经过 IPV4、URL_HTTP 正则匹配后的group
        // 例：http://www.baidu.com:80/s/test?param=1
        // URL_HTTP group：[http://www.baidu.com:80/s/test?param=1, http://, baidu., :80, /s/test?param=1]

        String addr = addrMsg.replace("ping", "").trim();
        List<String> ipv4Groups = ReUtil.getAllGroups(PatternPool.IPV4, addr),
                ipv6Groups = ReUtil.getAllGroups(PatternPool.IPV6, addr),
                urlGroups = ReUtil.getAllGroups(PatternPool.URL_HTTP, addr);

        // 必须先判IP再判URL，当IP匹配为空时才else URL，因为IP也能被URL正则识别
        if(null != ipv4Groups && ipv4Groups.size() > 0 && null != ipv4Groups.get(0)){
            // ping IPv4
            resList.add("1");
            resList.add(ipv4Groups.get(0));
        } else if(null != ipv6Groups && ipv6Groups.size() > 0 && null != ipv6Groups.get(0)){
            // ping IPv6
            resList.add("1");
            resList.add(ipv6Groups.get(0));
        }
        else if(null != urlGroups && urlGroups.size() > 0 && null != urlGroups.get(0)){
            // ping URL
            resList.add("1");
            resList.add(urlGroups.get(0));
        } else {
            // IP地址和URL链接都是错误的
            resList.add("0");
            resList.add("IP地址或URL链接错误！");
        }

        return resList;
    }

}
