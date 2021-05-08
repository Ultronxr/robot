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

    /**
     * 统计 某段时间内 <b>所有群<b/> 的活跃程度/排名Handler
     * 所有QQ群的活跃程度
     *
     * @param groupMsgEvent GroupMessageEvent群聊消息事件
     * @param msgCode       MiraiUtils自定义消息类型一
     * @param msgContent    MiraiUtils自定义消息类型二
     * @param msgPlain      MiraiUtils自定义消息类型三
     * @see cn.ultronxr.qqrobot.util.MiraiUtils
     */
    void statisticsAllGroup(GroupMessageEvent groupMsgEvent, String msgCode, String msgContent, String msgPlain);

    /**
     * 统计 某段时间内 某个群的<b>所有群成员<b/> 的活跃程度/排名Handler
     * 一个QQ群内所有群成员的活跃程度
     *
     * @param groupMsgEvent GroupMessageEvent群聊消息事件
     * @param msgCode       MiraiUtils自定义消息类型一
     * @param msgContent    MiraiUtils自定义消息类型二
     * @param msgPlain      MiraiUtils自定义消息类型三
     * @see cn.ultronxr.qqrobot.util.MiraiUtils
     */
    void statisticsGroupAllMember(GroupMessageEvent groupMsgEvent, String msgCode, String msgContent, String msgPlain);

}
