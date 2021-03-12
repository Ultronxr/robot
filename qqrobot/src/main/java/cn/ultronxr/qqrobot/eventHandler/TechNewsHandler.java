package cn.ultronxr.qqrobot.eventHandler;

import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.events.GroupMessageEvent;


/**
 * 科技日报 事件处理器Handler
 */
public interface TechNewsHandler {

    /**
     * 群聊中的 企查查早报/晚报 查询处理器
     *
     * 详细逻辑请查看 {@link cn.ultronxr.qqrobot.service.TechNewsService}
     *
     * @param groupMsgEvent GroupMessageEvent群聊消息事件
     * @param msgCode       MiraiUtils自定义消息类型一
     * @param msgContent    MiraiUtils自定义消息类型二
     * @param msgPlain      MiraiUtils自定义消息类型三
     * @see cn.ultronxr.qqrobot.util.MiraiUtils
     * @param type          企查查日报类型，1-早报、2-晚报
     * @return {@code ListeningStatus} 用于监听器判断是否继续监听
     */
    ListeningStatus groupQiChaChaNewsHandler(GroupMessageEvent groupMsgEvent, String msgCode, String msgContent, String msgPlain, Integer type);

}
