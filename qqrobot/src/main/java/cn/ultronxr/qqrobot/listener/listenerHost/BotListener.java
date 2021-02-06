package cn.ultronxr.qqrobot.listener.listenerHost;

import cn.ultronxr.qqrobot.bean.BotEntity;
import kotlin.coroutines.CoroutineContext;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.BotOfflineEvent;
import net.mamoe.mirai.event.events.BotReloginEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class BotListener extends SimpleListenerHost {


    /** 机器人离线监听器 */
    @EventHandler
    public ListeningStatus onBotOfflineEvent(BotOfflineEvent botOfflineEvent) throws Exception {
        String reason = null;

        if(botOfflineEvent instanceof BotOfflineEvent.Active){
            reason = "主动离线（BotOfflineEvent.Active）";
        } else if(botOfflineEvent instanceof BotOfflineEvent.Force){
            reason = "被挤下线（BotOfflineEvent.Force）";
        } else if(botOfflineEvent instanceof BotOfflineEvent.Dropped){
            reason = "被服务器断开或因网络问题而掉线（BotOfflineEvent.Dropped）";
        } else if(botOfflineEvent instanceof BotOfflineEvent.RequireReconnect){
            reason = "服务器主动要求更换另一个服务器（BotOfflineEvent.RequireReconnect）";
        }

        log.warn("[system] qqrobot机器人离线，离线原因：" + (reason ==null ? "" : reason));
        log.warn("[system] qqrobot尝试重新登录...");
        BotEntity.BOT_ENTITY.login();

        return ListeningStatus.LISTENING;
    }

    /** 机器人重新登录监听器 */
    @EventHandler
    public ListeningStatus onBotReloginEvent(BotReloginEvent botReloginEvent){
        log.info("[system] qqrobot机器人正在（重新）登录...");
        return ListeningStatus.LISTENING;
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
