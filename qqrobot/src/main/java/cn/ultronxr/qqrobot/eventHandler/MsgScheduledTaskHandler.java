package cn.ultronxr.qqrobot.eventHandler;

import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * 消息事件 - 定时任务处理器
 */
public interface MsgScheduledTaskHandler {

    /**
     * 发送 定时格式
     *
     * @param msgEvent 消息事件
     */
    void scheduledTaskFormat(MessageEvent msgEvent);

    /**
     * 发送 定时功能的详细解释说明
     *
     * @param msgEvent 消息事件
     */
    void scheduledTaskExplain(MessageEvent msgEvent);

    /**
     * 群聊中的 定时任务 处理器
     *
     * @param groupMsgEvent GroupMessageEvent群聊消息事件
     * @param msgCode       MiraiUtils自定义消息类型一
     * @param msgContent    MiraiUtils自定义消息类型二
     * @param msgPlain      MiraiUtils自定义消息类型三
     * @see cn.ultronxr.qqrobot.util.MiraiUtils
     */
    void groupScheduledTaskHandler(GroupMessageEvent groupMsgEvent, String msgCode, String msgContent, String msgPlain);

}
