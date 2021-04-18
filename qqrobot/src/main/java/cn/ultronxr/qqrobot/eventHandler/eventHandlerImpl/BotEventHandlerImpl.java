package cn.ultronxr.qqrobot.eventHandler.eventHandlerImpl;

import cn.ultronxr.qqrobot.eventHandler.BotEventHandler;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.BotOfflineEvent;
import net.mamoe.mirai.event.events.BotReloginEvent;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class BotEventHandlerImpl implements BotEventHandler {

    @Override
    public void botOfflineHandler(BotOfflineEvent botOfflineEvent) {
        String reason = "其他未分类或测试原因";

        if(botOfflineEvent instanceof BotOfflineEvent.Active){
            reason = "主动离线（BotOfflineEvent.Active）";
        } else if(botOfflineEvent instanceof BotOfflineEvent.Force){
            reason = "被挤下线（BotOfflineEvent.Force）";
        } else if(botOfflineEvent instanceof BotOfflineEvent.Dropped){
            reason = "被服务器断开或因网络问题而掉线（BotOfflineEvent.Dropped）";
        } else if(botOfflineEvent instanceof BotOfflineEvent.RequireReconnect){
            reason = "服务器主动要求更换另一个服务器（BotOfflineEvent.RequireReconnect）";
        }

        log.warn("[system] qqrobot机器人离线事件，原因：" + reason);
    }

    @Override
    public void botReloginHandler(BotReloginEvent botReloginEvent) {
        log.info("[system] qqrobot机器人（重新）登录事件");
    }
}
