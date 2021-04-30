package cn.ultronxr.qqrobot.eventHandler;

import net.mamoe.mirai.event.events.GroupMessageEvent;

/**
 * @author Ultronxr
 * @date 2021/04/30 15:52
 *
 * 消息事件 - 磁力链/种子搜索Handler
 */
public interface MsgMagnetHandler {

    /**
     * 群消息搜索磁力链
     *
     * @param groupMsgEvent GroupMessageEvent群聊消息事件
     * @param msgCode       MiraiUtils自定义消息类型一
     * @param msgContent    MiraiUtils自定义消息类型二
     * @param msgPlain      MiraiUtils自定义消息类型三
     * @see cn.ultronxr.qqrobot.util.MiraiUtils
     */
    void groupMagnetHandler(GroupMessageEvent groupMsgEvent, String msgCode, String msgContent, String msgPlain);

}
