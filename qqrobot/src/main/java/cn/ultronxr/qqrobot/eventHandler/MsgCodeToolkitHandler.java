package cn.ultronxr.qqrobot.eventHandler;

import cn.ultronxr.qqrobot.bean.BotCmd;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.apache.commons.cli.CommandLine;

/**
 * @author Ultronxr
 * @date 2022/02/18 13:33
 *
 * 消息事件 - 有关各种代码小工具的Handler，包括MD5、SHA-1、等等
 */
public interface MsgCodeToolkitHandler {

    /**
     * 套用BotCmd/BotMenu框架
     * 群聊中的 MD5 命令处理器
     *
     * @param botCmd        匹配成功的命令BotCmd对象
     * @param cmdLine       解析完成的CommandLine对象
     * @param groupMsgEvent 群消息事件
     * @param msgPlain      纯消息主体文本内容（MiraiUtils自定义消息类型三）
     *                      {@link cn.ultronxr.qqrobot.util.MiraiUtils#getMsgPlain(net.mamoe.mirai.event.events.MessageEvent)}
     */
    void groupMD5Handler(BotCmd botCmd, CommandLine cmdLine, GroupMessageEvent groupMsgEvent, String msgPlain);

    /**
     * 套用BotCmd/BotMenu框架
     * 群聊中的 URL转码/解码 命令处理器
     *
     * @param botCmd        匹配成功的命令BotCmd对象
     * @param cmdLine       解析完成的CommandLine对象
     * @param groupMsgEvent 群消息事件
     * @param msgPlain      纯消息主体文本内容（MiraiUtils自定义消息类型三）
     *                      {@link cn.ultronxr.qqrobot.util.MiraiUtils#getMsgPlain(net.mamoe.mirai.event.events.MessageEvent)}
     */
    void groupURLHandler(BotCmd botCmd, CommandLine cmdLine, GroupMessageEvent groupMsgEvent, String msgPlain);

    /**
     * 套用BotCmd/BotMenu框架
     * 群聊中的 Unicode转码/解码 命令处理器
     *
     * @param botCmd        匹配成功的命令BotCmd对象
     * @param cmdLine       解析完成的CommandLine对象
     * @param groupMsgEvent 群消息事件
     * @param msgPlain      纯消息主体文本内容（MiraiUtils自定义消息类型三）
     *                      {@link cn.ultronxr.qqrobot.util.MiraiUtils#getMsgPlain(net.mamoe.mirai.event.events.MessageEvent)}
     */
    void groupUnicodeHandler(BotCmd botCmd, CommandLine cmdLine, GroupMessageEvent groupMsgEvent, String msgPlain);

}
