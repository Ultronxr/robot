package cn.ultronxr.qqrobot.eventHandler;

import net.mamoe.mirai.event.events.GroupMessageEvent;


/**
 * 图片处理器Handler
 */
public interface PicHandler {

    /**
     * 群聊中的图片处理器
     *
     * @param groupMsgEvent GroupMessageEvent群聊消息事件
     * @param msgCode       MiraiUtils自定义消息类型一
     * @param msgContent    MiraiUtils自定义消息类型二
     * @param msgPlain      MiraiUtils自定义消息类型三
     * @see cn.ultronxr.qqrobot.util.MiraiUtils
     */
    void groupPicHandler(GroupMessageEvent groupMsgEvent, String msgCode, String msgContent, String msgPlain);

}
