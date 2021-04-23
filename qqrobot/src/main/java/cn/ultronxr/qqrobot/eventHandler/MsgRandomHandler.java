package cn.ultronxr.qqrobot.eventHandler;

import net.mamoe.mirai.event.events.GroupMessageEvent;

/**
 * @author Ultronxr
 * @date 2021/04/23 11:48
 *
 * 消息事件 - 获取随机内容handler
 */
public interface MsgRandomHandler {

    /**
     * 群消息获取随机整数
     *
     * @param groupMsgEvent GroupMessageEvent群聊消息事件
     * @param msgCode       MiraiUtils自定义消息类型一
     * @param msgContent    MiraiUtils自定义消息类型二
     * @param msgPlain      MiraiUtils自定义消息类型三
     * @see cn.ultronxr.qqrobot.util.MiraiUtils
     */
    void groupRandomNumberHandler(GroupMessageEvent groupMsgEvent, String msgCode, String msgContent, String msgPlain);

    /**
     * 群消息获取随机密码
     *
     * @param groupMsgEvent GroupMessageEvent群聊消息事件
     * @param msgCode       MiraiUtils自定义消息类型一
     * @param msgContent    MiraiUtils自定义消息类型二
     * @param msgPlain      MiraiUtils自定义消息类型三
     * @see cn.ultronxr.qqrobot.util.MiraiUtils
     */
    void groupRandomSecretHandler(GroupMessageEvent groupMsgEvent, String msgCode, String msgContent, String msgPlain);

}
