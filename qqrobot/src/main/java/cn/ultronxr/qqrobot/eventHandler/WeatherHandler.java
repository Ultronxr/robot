package cn.ultronxr.qqrobot.eventHandler;

import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.events.GroupMessageEvent;


/**
 * 天气查询处理器Handler
 */
public interface WeatherHandler {

    /**
     * 群聊中的天气查询处理器
     *
     * @param groupMsgEvent GroupMessageEvent群聊消息事件
     * @param msgCode       MiraiUtils自定义消息类型一
     * @param msgContent    MiraiUtils自定义消息类型二
     * @param msgPlain      MiraiUtils自定义消息类型三
     * @see cn.ultronxr.qqrobot.util.MiraiUtils
     * @return 返回一个ListeningStatus监听状态，用于监听器判断是否继续监听
     */
    ListeningStatus groupWeatherHandler(GroupMessageEvent groupMsgEvent, String msgCode, String msgContent, String msgPlain);

}
