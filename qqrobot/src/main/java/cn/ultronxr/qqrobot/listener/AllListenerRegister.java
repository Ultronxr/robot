package cn.ultronxr.qqrobot.listener;

import cn.ultronxr.qqrobot.bean.BotEntity;
import cn.ultronxr.qqrobot.eventHandler.BotEventHandler;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author Ultronxr
 * @date 2021/02/09 01:09
 *
 * 这里是事件通道（EventChannel）的总列表，所有事件都在这里注册
 *
 * 事件通道监听事件：
 *   EventChannel.subscribe：监听事件并自行觉得何时停止
 *   EventChannel.subscribeAlways：一直监听事件
 *   EventChannel.subscribeOnce：只监听一次事件
 *   {@link "https://github.com/mamoe/mirai/blob/dev/docs/Events.md#%E5%9C%A8-eventchannel-%E7%9B%91%E5%90%AC%E4%BA%8B%E4%BB%B6"}
 * BOT支持的所有事件列表参阅 {@link "https://github.com/mamoe/mirai/blob/dev/mirai-core-api/src/commonMain/kotlin/event/events/README.md"}
 */
@Component
@Slf4j
public class AllListenerRegister implements ApplicationRunner {

    @Autowired
    private BotEventHandler botEventHandler;

    @Autowired
    private GroupInfoListener groupInfoListener;

    @Autowired
    private MsgGroupListener msgGroupListener;

    @Autowired
    private MsgFriendListener msgFriendListener;


    /**
     * 实现自 {@code ApplicationRunner} 的 {@code run(ApplicationArguments args)} 方法会同 {@code QqrobotApplication} 一起启动
     *
     * 内容较少的事件，事件通道直接调用Handler注册
     * 内容较多的事件，事件通道调用XxxListener进行预处理再注册
     */
    @Override
    public void run(ApplicationArguments args) {
        EventChannel<BotEvent> eventChannel = BotEntity.BOT_ENTITY.getEventChannel();

        // BOT事件
        eventChannel.subscribeAlways(BotOfflineEvent.class, botOfflineEvent -> botEventHandler.botOfflineHandler(botOfflineEvent));
        eventChannel.subscribeAlways(BotReloginEvent.class, botReloginEvent -> botEventHandler.botReloginHandler(botReloginEvent));

        // 群事件
        eventChannel.subscribeAlways(MemberJoinEvent.class, memberJoinEvent -> groupInfoListener.onMemberJoin(memberJoinEvent));
        eventChannel.subscribeAlways(MemberLeaveEvent.class, memberLeaveEvent -> groupInfoListener.onMemberLeave(memberLeaveEvent));

        // 消息事件
        eventChannel.subscribeAlways(GroupMessageEvent.class, groupMsgEvent -> msgGroupListener.onGroupMessage(groupMsgEvent));

    }
}
