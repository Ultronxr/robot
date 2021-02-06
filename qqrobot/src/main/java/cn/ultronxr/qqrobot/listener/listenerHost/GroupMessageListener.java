//package cn.ultronxr.qqrobot.listener.listenerHost;
//
//import cn.ultronxr.qqrobot.bean.BotEntity;
//import cn.ultronxr.qqrobot.eventHandler.*;
//import lombok.extern.slf4j.Slf4j;
//import net.mamoe.mirai.event.EventHandler;
//import net.mamoe.mirai.event.ListeningStatus;
//import net.mamoe.mirai.event.SimpleListenerHost;
//import org.jetbrains.annotations.NotNull;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//
//@Component
//@Slf4j
//public class GroupMessageListener {
//
//    @Autowired
//    private PicHandler picHandler;
//
//    @Autowired
//    private PingHandler pingHandler;
//
//    @Autowired
//    private RobotMenuHandler robotMenuHandler;
//
//    @Autowired
//    private SentenceHandler sentenceHandler;
//
//    @Autowired
//    private WeatherHandler weatherHandler;
//
//
//    /**
//     * 为机器人构建事件监听通道
//     *
//     */
//        BotEntity.BOT_ENTITY.getEventChannel().registerListenerHost(
//            new SimpleListenerHost() {
//                @EventHandler
//                public ListeningStatus onGroupMessage(GroupMessageEvent groupMsgEvent){
//                    //String labeledMsg = MiraiUtils.getLabeledMsg(groupMsgEvent),
//                    //        unlabeledMsg = MiraiUtils.getUnlabeledMsg(groupMsgEvent),
//                    //        plainMsg = MiraiUtils.getPlainMsg(labeledMsg);
//                    //请勿在全局打印消息记录
//
//                    log.info("收到群消息");
//                    log.info(groupMsgEvent.getMessage().contentToString());
//                    log.info(groupMsgEvent.getMessage().toString());
//
//                    //@机器人，并提及关键词触发的事件处理Handler
//                    //if(labeledMsg.contains(MiraiLabel.BOT_AT.getContent())) {
//                    //
//                    //    log.info("[message-receive] labeledMsg: "+ labeledMsg);
//                    //    log.info("[message-receive] unlabeledMsg: "+ unlabeledMsg);
//                    //    log.info("[message-receive] plainMsg: " + plainMsg);
//                    //
//                    //    if(plainMsg.contains("功能") || plainMsg.contains("菜单")){
//                    //        log.info("[function] 查询机器人功能菜单。");
//                    //        robotMenuHandler.robotMenuGroupHandler(groupMsgEvent, labeledMsg, unlabeledMsg, plainMsg);
//                    //    }
//                    //    if(plainMsg.contains("天气")){
//                    //        weatherHandler.weatherGroupHandler(groupMsgEvent, labeledMsg, unlabeledMsg, plainMsg);
//                    //    }
//                    //    if(plainMsg.contains("ping")){
//                    //        pingHandler.pingGroupHandler(groupMsgEvent, labeledMsg, unlabeledMsg, plainMsg);
//                    //    }
//                    //    if(plainMsg.contains("图片")){
//                    //        picHandler.picGroupHandler(groupMsgEvent, labeledMsg, unlabeledMsg, plainMsg);
//                    //    }
//                    //    if(plainMsg.contains("舔狗") || plainMsg.contains("彩虹屁")){
//                    //        sentenceHandler.sentenceFlatterGroupHandler(groupMsgEvent, labeledMsg, unlabeledMsg, plainMsg);
//                    //    }
//                    //    if(plainMsg.contains("脏话") || plainMsg.contains("口吐芬芳") || plainMsg.contains("芬芳")){
//                    //        sentenceHandler.sentenceAbuseGroupHandler(groupMsgEvent, labeledMsg, unlabeledMsg, plainMsg, 1);
//                    //    }
//                    //    if(plainMsg.contains("火力全开")){
//                    //        sentenceHandler.sentenceAbuseGroupHandler(groupMsgEvent, labeledMsg, unlabeledMsg, plainMsg, 2);
//                    //    }
//                    //}
//
//                    return ListeningStatus.LISTENING;
//                }
//            }
//
//}
