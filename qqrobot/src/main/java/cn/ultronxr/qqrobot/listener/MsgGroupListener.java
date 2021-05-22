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

        // @机器人消息事件
        //if(MiraiUtils.isGroupAtBot(groupMsgEvent)){
        ////if(msgPlain.startsWith(">")){
        //    log.info("[message-receive] msgCode: " + msgCode);
        //    log.info("[message-receive] msgContent: "+ msgContent);
        //    log.info("[message-receive] msgPlain: "+ msgPlain);
        //    //log.info("[message-receive] msgStr: " + msgStr);
        //
        //    if(msgPlain.contains("功能") || msgPlain.contains("菜单")){
        //        msgRobotMenuHandler.groupRobotMenuHandler(groupMsgEvent, msgCode, msgContent, msgPlain);
        //    }
        //    if(msgPlain.contains("天气")){
        //        msgWeatherHandler.groupWeatherHandler(groupMsgEvent, msgPlain);
        //    }
        //    if(msgPlain.startsWith("ping")){
        //        msgShellCmdHandler.groupPingHandler(groupMsgEvent, msgCode, msgContent, msgPlain);
        //    }
        //    if(msgPlain.startsWith("shell") || msgPlain.startsWith(">")){
        //        msgShellCmdHandler.groupShellCmdHandler(groupMsgEvent, msgCode, msgContent, msgPlain);
        //    }
        //    if(msgPlain.contains("舔狗") || msgPlain.contains("彩虹屁")){
        //        msgSentenceHandler.groupSentenceFlatterHandler(groupMsgEvent, msgCode, msgContent, msgPlain);
        //    }
        //    if(msgPlain.contains("脏话") || msgPlain.contains("口吐芬芳") || msgPlain.contains("芬芳")){
        //        msgSentenceHandler.groupSentenceAbuseHandler(groupMsgEvent, msgCode, msgContent, msgPlain, 1);
        //    }
        //    if(msgPlain.contains("火力全开")){
        //        msgSentenceHandler.groupSentenceAbuseHandler(groupMsgEvent, msgCode, msgContent, msgPlain, 2);
        //    }
        //    if(msgPlain.contains("定时")){
        //        if(msgPlain.contains("定时格式")){
        //            msgScheduledTaskHandler.scheduledTaskFormat(groupMsgEvent);
        //        } else if(msgPlain.contains("定时说明")){
        //            msgScheduledTaskHandler.scheduledTaskExplain(groupMsgEvent);
        //        } else {
        //            msgScheduledTaskHandler.groupScheduledTaskHandler(groupMsgEvent, msgCode, msgContent, msgPlain);
        //        }
        //    }
        //    if(msgPlain.startsWith("随机数") || msgPlain.startsWith("random")){
        //        msgRandomHandler.groupRandomNumberHandler(groupMsgEvent, msgCode, msgContent, msgPlain);
        //    }
        //    if(msgPlain.startsWith("phub")){
        //        msgImgHandler.groupImgPornHubIconHandler(groupMsgEvent, msgCode, msgContent, msgPlain);
        //    }
        //    if(msgPlain.startsWith("磁力") || msgPlain.startsWith("种子") || msgPlain.startsWith("车牌") || msgPlain.startsWith("magnet")){
        //        msgMagnetHandler.groupMagnetHandler(groupMsgEvent, msgCode, msgContent, msgPlain);
        //    }
        //    if(msgPlain.startsWith("所有群活跃")) {
        //        msgGroupChatStatisticsHandler.statisticsAllGroup(groupMsgEvent, msgCode, msgContent, msgPlain);
        //    }
        //    if(msgPlain.startsWith("群活跃") || msgPlain.startsWith("水群排行") || msgPlain.startsWith("水群排名")) {
        //        msgGroupChatStatisticsHandler.statisticsGroupAllMember(groupMsgEvent, msgCode, msgContent, msgPlain);
        //    }
        //}
    }
}
