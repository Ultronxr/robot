package cn.ultronxr.qqrobot.eventHandler;

import cn.ultronxr.qqrobot.bean.BotCmd;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import org.jetbrains.annotations.NotNull;

/**
 * @author Ultronxr
 * @date 2021/05/08 13:38
 *
 * BOT功能命令BotCmd统一解析和处理 Handler
 */
public interface BotCmdHandler {

    /**
     * 对功能命令BotCmd进行统一解析和处理
     *
     * @param botCmd        BOT功能命令对象
     *                      {@link BotCmd}
     *
     * @param groupMsgEvent 群消息事件
     *                      {@link GroupMessageEvent}
     *
     * @param msgPlain      自定义的第三种群消息类别（纯消息主体内容）
     *                      {@link cn.ultronxr.qqrobot.util.MiraiUtils#getMsgPlain(MessageEvent)}
     */
    void botCmdHandler(@NotNull BotCmd botCmd, @NotNull GroupMessageEvent groupMsgEvent, @NotNull String msgPlain);

}
