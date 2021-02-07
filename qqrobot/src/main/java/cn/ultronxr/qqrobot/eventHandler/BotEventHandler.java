package cn.ultronxr.qqrobot.eventHandler;

import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.events.BotOfflineEvent;
import net.mamoe.mirai.event.events.BotReloginEvent;

/**
 * 有关机器人BOT层的事件处理器
 */
public interface BotEventHandler {

    /**
     * BOT离线事件处理器
     * @param botOfflineEvent 机器人离线事件
     * @return 返回一个ListeningStatus监听状态，用于监听器判断是否继续监听
     */
    ListeningStatus botOfflineHandler(BotOfflineEvent botOfflineEvent);

    /**
     * BOT（重新）登录事件处理器
     * @param botReloginEvent 机器人（重新）登录事件
     * @return 返回一个ListeningStatus监听状态，用于监听器判断是否继续监听
     */
    ListeningStatus botReloginHandler(BotReloginEvent botReloginEvent);

}
