package cn.ultronxr.qqrobot.eventHandler;

import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.apache.commons.cli.Options;


/**
 * 消息事件 - 天气查询处理器Handler
 */
public interface MsgWeatherHandler {

    /**
     * 群聊中的天气查询处理器
     *
     * @param groupMsgEvent GroupMessageEvent群聊消息事件
     * @param msgPlain      MiraiUtils自定义消息类型三
     * @see cn.ultronxr.qqrobot.util.MiraiUtils
     */
    void groupWeatherHandler(GroupMessageEvent groupMsgEvent, String msgPlain);

    void groupWeatherHandler(GroupMessageEvent groupMsgEvent, String msgPlain, Options options);

}
