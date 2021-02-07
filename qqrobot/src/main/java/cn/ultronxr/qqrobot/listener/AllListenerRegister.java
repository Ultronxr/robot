package cn.ultronxr.qqrobot.listener;

import cn.ultronxr.qqrobot.bean.BotEntity;
import cn.ultronxr.qqrobot.listener.listenerHost.BotListener;
import cn.ultronxr.qqrobot.listener.listenerHost.GroupMsgListener;
import cn.ultronxr.qqrobot.listener.listenerHost.PersonMsgListener;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.BotEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


/**
 * 这里是事件监听器（listenerHost）的总列表，所有事件监听器都在这里注册
 *
 * 在Application启动时同时运行{@code run(ApplicationArguments args)}方法，
 * 在run()方法中为机器人构建事件监听通道。
 * {@link "https://github.com/mamoe/mirai/blob/dev/docs/Events.md#%E7%9B%91%E5%90%AC%E4%BA%8B%E4%BB%B6%E7%9A%84%E5%85%B6%E4%BB%96%E6%96%B9%E6%B3%95"}
 */
@Component
@Slf4j
public class AllListenerRegister implements ApplicationRunner {

    @Autowired
    private BotListener botListener;

    @Autowired
    private GroupMsgListener groupMsgListener;

    @Autowired
    private PersonMsgListener personMsgListener;


    @Override
    public void run(ApplicationArguments args) {

        EventChannel<BotEvent> eventChannel = BotEntity.BOT_ENTITY.getEventChannel();
        eventChannel.registerListenerHost(botListener);
        eventChannel.registerListenerHost(groupMsgListener);
        //eventChannel.registerListenerHost(personMsgListener);


        //后续转向这种新版监听通道，目前先采用上面的方法
        //eventChannel.subscribeAlways(GroupMessageEvent.class, groupMsgEvent -> {});
    }
}
