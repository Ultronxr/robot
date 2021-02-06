package cn.ultronxr.qqrobot.eventHandler;

import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.events.GroupMessageEvent;

/**
 * 语句处理器Handler
 */
public interface SentenceHandler {

    /**
     * 群聊中的 舔狗语句 处理器
     *
     * @param groupMsgEvent GroupMessageEvent群聊消息事件
     * @param labeledMsg    MiraiUtils自定义消息类型一
     * @param unlabeledMsg  MiraiUtils自定义消息类型二
     * @param plainMsg      MiraiUtils自定义消息类型三
     * @see cn.ultronxr.qqrobot.util.MiraiUtils
     * @return 返回一个ListeningStatus监听状态，用于监听器判断是否继续监听
     */
    ListeningStatus sentenceFlatterGroupHandler(GroupMessageEvent groupMsgEvent, String labeledMsg, String unlabeledMsg, String plainMsg);

    /**
     * 群聊中的 脏话语句 处理器
     *
     * @param groupMsgEvent GroupMessageEvent群聊消息事件
     * @param labeledMsg    MiraiUtils自定义消息类型一
     * @param unlabeledMsg  MiraiUtils自定义消息类型二
     * @param plainMsg      MiraiUtils自定义消息类型三
     * @see cn.ultronxr.qqrobot.util.MiraiUtils
     * @param type          脏话级别
     *                      1 - 口吐芬芳；2 - 火力全开
     * @return 返回一个ListeningStatus监听状态，用于监听器判断是否继续监听
     */
    ListeningStatus sentenceAbuseGroupHandler(GroupMessageEvent groupMsgEvent, String labeledMsg, String unlabeledMsg, String plainMsg, Integer type);

}
