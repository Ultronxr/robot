package cn.ultronxr.qqrobot.eventHandler;

import net.mamoe.mirai.event.events.MemberJoinEvent;


/**
 * 群事件 - 群聊信息变更事件Handler，包括 群设置、群成员 等
 * （这里的所有方法都针对群聊事件）
 * {@link "https://github.com/mamoe/mirai/blob/dev/mirai-core-api/src/commonMain/kotlin/event/events/README.md#%E7%BE%A4"}
 */
public interface GroupInfoHandler {

    /**
     * 新成员主动入群事件处理器
     *
     * @param memberJoinActiveEvent 新成员入群事件
     */
    void memberJoinActive(MemberJoinEvent.Active memberJoinActiveEvent);

    /**
     * 新成员被邀请入群事件处理器
     *
     * @param memberJoinInviteEvent 新成员入群事件
     */
    void memberJoinInvite(MemberJoinEvent.Invite memberJoinInviteEvent);

}
