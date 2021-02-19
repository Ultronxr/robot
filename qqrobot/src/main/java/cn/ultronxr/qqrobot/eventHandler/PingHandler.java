package cn.ultronxr.qqrobot.eventHandler;

import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.events.GroupMessageEvent;


/**
 * ping命令处理器Handler
 */
public interface PingHandler {

    /**
     * 群聊中的ping命令处理器
     * 注意：自动修正ping地址参数，使用正则从消息中提取出正确的IP地址或URL链接，
     *      会自动忽略可能导致命令执行错误的内容（如协议标识、端口号等），不像命令行里一样返回各种错误
     *
     * @param groupMsgEvent GroupMessageEvent群聊消息事件
     * @param msgCode       MiraiUtils自定义消息类型一
     * @param msgContent    MiraiUtils自定义消息类型二
     * @param msgPlain      MiraiUtils自定义消息类型三
     * @see cn.ultronxr.qqrobot.util.MiraiUtils
     * @return 返回一个ListeningStatus监听状态，用于监听器判断是否继续监听
     */
    ListeningStatus groupPingHandler(GroupMessageEvent groupMsgEvent, String msgCode, String msgContent, String msgPlain);

}
