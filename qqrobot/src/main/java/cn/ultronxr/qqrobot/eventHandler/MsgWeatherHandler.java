package cn.ultronxr.qqrobot.eventHandler;

import cn.ultronxr.qqrobot.bean.BotCmd;
import net.mamoe.mirai.event.events.GroupMessageEvent;


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

    //void groupWeatherHandler(BotCmd botCmd, GroupMessageEvent groupMsgEvent);

}
