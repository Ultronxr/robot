package cn.ultronxr.qqrobot.listener.listenerHost;

import cn.ultronxr.qqrobot.eventHandler.BotEventHandler;
import kotlin.coroutines.CoroutineContext;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.BotOfflineEvent;
import net.mamoe.mirai.event.events.BotReloginEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 有关机器人BOT层的事件监听器
 */
@Component
@Slf4j
public class BotListener extends SimpleListenerHost {

    @Autowired
    private BotEventHandler botEventHandler;


    /** 机器人离线监听器 */
    @EventHandler
    public ListeningStatus onBotOfflineEvent(BotOfflineEvent botOfflineEvent) throws Exception {
        return botEventHandler.botOfflineHandler(botOfflineEvent);
    }

    /** 机器人重新登录监听器 */
    @EventHandler
    public ListeningStatus onBotReloginEvent(BotReloginEvent botReloginEvent){
        return botEventHandler.botReloginHandler(botReloginEvent);
    }


    /**
     * 异常处理
     * 上面的所有EventHandler中抛出的Exception都在这里接收并处理
     */
    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        super.handleException(context, exception);
    }
}
