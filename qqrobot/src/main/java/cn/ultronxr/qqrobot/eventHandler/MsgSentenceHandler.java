package cn.ultronxr.qqrobot.eventHandler;

import net.mamoe.mirai.event.events.GroupMessageEvent;


/**
 * 消息事件 - 语句处理器Handler
 */
public interface MsgSentenceHandler {

    /**
     * 群聊中的 舔狗语句 处理器
     *
     * @param groupMsgEvent GroupMessageEvent群聊消息事件
     * @param msgCode       MiraiUtils自定义消息类型一
     * @param msgContent    MiraiUtils自定义消息类型二
     * @param msgPlain      MiraiUtils自定义消息类型三
     * @see cn.ultronxr.qqrobot.util.MiraiUtils
     */
    void groupSentenceFlatterHandler(GroupMessageEvent groupMsgEvent, String msgCode, String msgContent, String msgPlain);

    /**
     * 群聊中的 脏话语句 处理器
     *
     * @param groupMsgEvent GroupMessageEvent群聊消息事件
     * @param msgCode       MiraiUtils自定义消息类型一
     * @param msgContent    MiraiUtils自定义消息类型二
     * @param msgPlain      MiraiUtils自定义消息类型三
     * @see cn.ultronxr.qqrobot.util.MiraiUtils
     * @param type          脏话级别
     *                      1 - 口吐芬芳；2 - 火力全开
     */
    void groupSentenceAbuseHandler(GroupMessageEvent groupMsgEvent, String msgCode, String msgContent, String msgPlain, Integer type);

    void groupSentenceAbuseHandler(Integer type);

}
