package cn.ultronxr.qqrobot.listener;

import cn.ultronxr.qqrobot.bean.BotCmd;
import cn.ultronxr.qqrobot.bean.BotMenu;
import cn.ultronxr.qqrobot.eventHandler.*;
import cn.ultronxr.qqrobot.util.MiraiUtils;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Ultronxr
 * @date 2021/02/09 01:09
 *
 * 消息事件 - 群聊消息事件监听器
 * 对群聊消息中的特殊关键词进行筛选和预处理，调用对应的消息事件处理器 MsgXxxHandler
 */
@Component
@Slf4j
public class MsgGroupListener {

    @Autowired
    private BotCmdHandler botCmdHandler;

    @Autowired
    private MsgGroupChatStatisticsHandler msgGroupChatStatisticsHandler;


    /**
     * 群聊消息事件监听器
     * 筛选对机器人的动作（@等）、各种关键词，从而调用不同的事件处理器，执行不同的回复
     */
    public void onGroupMessage(@NotNull GroupMessageEvent groupMsgEvent) {
        // 请避免在群聊全局打印消息记录
        String msgPlain = MiraiUtils.getMsgPlain(groupMsgEvent);

        // 群消息活跃统计
        msgGroupChatStatisticsHandler.groupChatStatisticsHandler(groupMsgEvent);

        if(msgPlain.startsWith(">")) {
            msgPlain = msgPlain.replaceFirst(">", "").strip();
            log.info("[Msg-Receive] msgPlain: {}", msgPlain);

            BotCmd botCmd = BotMenu.checkBotCmd(msgPlain);
            if(botCmd != null) {
                botCmdHandler.botCmdHandler(botCmd, groupMsgEvent, msgPlain);
                return;
            }
            String resMsg = "无匹配命令，请检查命令名称或格式。";
            groupMsgEvent.getSubject().sendMessage(resMsg);
            log.info("[Msg-Send] {}", resMsg);
        }
    }
}
