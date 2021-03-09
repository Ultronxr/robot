package cn.ultronxr.qqrobot.eventHandler;

import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * 定时任务处理器
 */
public interface ScheduledTaskHandler {

    /**
     * 发送 定时格式
     *
     * @param msgEvent 消息事件
     * @return {@code ListeningStatus} 用于监听器判断是否继续监听
     */
    ListeningStatus scheduledTaskFormat(MessageEvent msgEvent);

    /**
     * 发送 定时功能的详细解释说明
     *
     * @param msgEvent 消息事件
     * @return {@code ListeningStatus} 用于监听器判断是否继续监听
     */
    ListeningStatus scheduledTaskExplain(MessageEvent msgEvent);

    /**
     * 群聊中的 定时任务 处理器
     *
     * @param groupMsgEvent GroupMessageEvent群聊消息事件
     * @param msgCode       MiraiUtils自定义消息类型一
     * @param msgContent    MiraiUtils自定义消息类型二
     * @param msgPlain      MiraiUtils自定义消息类型三
     * @see cn.ultronxr.qqrobot.util.MiraiUtils
     * @return {@code ListeningStatus} 用于监听器判断是否继续监听
     */
    ListeningStatus groupScheduledTaskHandler(GroupMessageEvent groupMsgEvent, String msgCode, String msgContent, String msgPlain);

}
