package cn.ultronxr.qqrobot.eventHandler;

import cn.ultronxr.qqrobot.bean.BotCmd;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.apache.commons.cli.CommandLine;

/**
 * @author Ultronxr
 * @date 2022/03/04 17:17
 *
 * 消息事件 - 自定义提醒Handler
 */
public interface MsgReminderHandler {

    /**
     * 套用BotCmd/BotMenu框架
     * 群聊中的 提肛提醒小助手 命令处理器
     *
     * @param botCmd        匹配成功的命令BotCmd对象
     * @param cmdLine       解析完成的CommandLine对象
     * @param groupMsgEvent 群消息事件
     * @param msgPlain      纯消息主体文本内容（MiraiUtils自定义消息类型三）
     *                      {@link cn.ultronxr.qqrobot.util.MiraiUtils#getMsgPlain(net.mamoe.mirai.event.events.MessageEvent)}
     */
    void groupReminderLevatorAniMuscleTrainingHandler(BotCmd botCmd, CommandLine cmdLine, GroupMessageEvent groupMsgEvent, String msgPlain);

}
