package cn.ultronxr.qqrobot.eventHandler;

import net.mamoe.mirai.event.events.GroupMessageEvent;

/**
 * @author Ultronxr
 * @date 2021/05/05 17:20
 *
 * 消息事件 - 群/群成员（发言）活跃统计Handler
 */
public interface MsgGroupChatStatisticsHandler {

    /**
     * 群/群成员（发言）活跃统计Handler
     *
     * @param groupMsgEvent GroupMessageEvent群聊消息事件
     */
    void groupChatStatisticsHandler(GroupMessageEvent groupMsgEvent);

}
