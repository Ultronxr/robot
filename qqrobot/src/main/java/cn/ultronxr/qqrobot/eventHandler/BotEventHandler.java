package cn.ultronxr.qqrobot.eventHandler;

import net.mamoe.mirai.event.events.BotOfflineEvent;
import net.mamoe.mirai.event.events.BotReloginEvent;

/**
 * @author Ultronxr
 * @date 2021/02/06 17:38
 *
 * BOT事件 - 有关机器人BOT层的事件处理器
 */
public interface BotEventHandler {

    /**
     * BOT离线事件处理器
     * @param botOfflineEvent 机器人离线事件
     */
    void botOfflineHandler(BotOfflineEvent botOfflineEvent);

    /**
     * BOT（重新）登录事件处理器
     * @param botReloginEvent 机器人（重新）登录事件
     */
    void botReloginHandler(BotReloginEvent botReloginEvent);

}
