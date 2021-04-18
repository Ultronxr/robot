package cn.ultronxr.qqrobot.listener;

import cn.ultronxr.qqrobot.bean.BotEntity;
import cn.ultronxr.qqrobot.eventHandler.BotEventHandler;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.event.events.BotOfflineEvent;
import net.mamoe.mirai.event.events.BotReloginEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


/**
 * 这里是事件通道（EventChannel）的总列表，所有事件都在这里注册
 *
 * 实现自 {@code ApplicationRunner} 的 {@code run(ApplicationArguments args)} 方法会同 {@code QqrobotApplication} 一起启动
 *
 * EventChannel.subscribe：监听事件并自行觉得何时停止
 * EventChannel.subscribeAlways：一直监听事件
 * EventChannel.subscribeOnce：只监听一次事件
 * {@link "https://github.com/mamoe/mirai/blob/dev/docs/Events.md#%E5%9C%A8-eventchannel-%E7%9B%91%E5%90%AC%E4%BA%8B%E4%BB%B6"}
 */
@Component
@Slf4j
public class AllListenerRegister implements ApplicationRunner {

    @Autowired
    private BotEventHandler botEventHandler;

    @Autowired
    private GroupMsgListener groupMsgListener;

    @Autowired
    private PersonMsgListener personMsgListener;


    @Override
    public void run(ApplicationArguments args) {
        EventChannel<BotEvent> eventChannel = BotEntity.BOT_ENTITY.getEventChannel();

        // BOT事件较少，事件通道直接调用Handler
        eventChannel.subscribeAlways(BotOfflineEvent.class, botOfflineEvent -> {
            botEventHandler.botOfflineHandler(botOfflineEvent);
        });
        eventChannel.subscribeAlways(BotReloginEvent.class, botReloginEvent -> {
            botEventHandler.botReloginHandler(botReloginEvent);
        });

        // GroupMessage事件较多，事件通道使用GroupMsgListener进行预处理
        eventChannel.subscribeAlways(GroupMessageEvent.class, groupMsgEvent -> {
            groupMsgListener.onGroupMessage(groupMsgEvent);
        });

    }
}
