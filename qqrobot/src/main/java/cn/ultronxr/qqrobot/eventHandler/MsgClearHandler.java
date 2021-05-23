package cn.ultronxr.qqrobot.eventHandler;

import cn.ultronxr.qqrobot.bean.BotCmd;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.apache.commons.cli.CommandLine;

/**
 * @author Ultronxr
 * @date 2021/05/23 11:18
 *
 * 消息事件 - 清屏clear命令事件 handler
 */
public interface MsgClearHandler {

    /**
     * 套用BotCmd/BotMenu框架
     * 群聊中的 清屏clear 命令处理器
     *
     * @param botCmd        匹配成功的命令BotCmd对象
     * @param idx           Options参数组/对应处理方法Method的列表下标idx
     * @param cmdLine       解析完成的CommandLine对象
     * @param groupMsgEvent 群消息事件
     * @param msgPlain      纯消息主体文本内容（MiraiUtils自定义消息类型三）
     *                      {@link cn.ultronxr.qqrobot.util.MiraiUtils#getMsgPlain(net.mamoe.mirai.event.events.MessageEvent)}
     */
    void groupClearHandler(BotCmd botCmd, Integer idx, CommandLine cmdLine, GroupMessageEvent groupMsgEvent, String msgPlain);

    /**
     * 群聊中的 清屏clear 命令-help处理器
     *
     * @param botCmd        匹配成功的命令BotCmd对象
     * @param idx           Options参数组/对应处理方法Method的列表下标idx
     * @param cmdLine       解析完成的CommandLine对象
     * @param groupMsgEvent 群消息事件
     * @param msgPlain      纯消息主体文本内容（MiraiUtils自定义消息类型三）
     *                      {@link cn.ultronxr.qqrobot.util.MiraiUtils#getMsgPlain(net.mamoe.mirai.event.events.MessageEvent)}
     */
    void groupClearHelper(BotCmd botCmd, Integer idx, CommandLine cmdLine, GroupMessageEvent groupMsgEvent, String msgPlain);

}
