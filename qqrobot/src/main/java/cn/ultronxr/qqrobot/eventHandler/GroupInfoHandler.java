package cn.ultronxr.qqrobot.eventHandler;

import net.mamoe.mirai.event.events.MemberJoinEvent;
import net.mamoe.mirai.event.events.MemberLeaveEvent;

/**
 * @author Ultronxr
 * @date 2021/04/18 10:25
 *
 * 群事件 - 群聊信息变更事件Handler，包括 群设置、群成员 等
 * （这里的所有方法都针对群聊事件）
 * {@link "https://github.com/mamoe/mirai/blob/dev/mirai-core-api/src/commonMain/kotlin/event/events/README.md#%E7%BE%A4"}
 */
public interface GroupInfoHandler {

    /**
     * 新成员被邀请入群事件处理器
     *
     * @param memberJoinInviteEvent 新成员被邀请入群事件
     */
    void memberJoinInvite(MemberJoinEvent.Invite memberJoinInviteEvent);

    /**
     * 新成员主动入群事件处理器
     *
     * @param memberJoinActiveEvent 新成员主动入群事件
     */
    void memberJoinActive(MemberJoinEvent.Active memberJoinActiveEvent);

    /**
     * 群成员被踢退群事件处理器
     *
     * @param memberLeaveKickEvent 群成员被踢退群事件
     */
    void memberLeaveKick(MemberLeaveEvent.Kick memberLeaveKickEvent);

    /**
     * 群成员主动退群事件处理器
     *
     * @param memberLeaveQuitEvent 群成员主动退群事件
     */
    void memberLeaveQuit(MemberLeaveEvent.Quit memberLeaveQuitEvent);

}
