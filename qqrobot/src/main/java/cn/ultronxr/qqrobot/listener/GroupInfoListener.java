package cn.ultronxr.qqrobot.listener;

import cn.ultronxr.qqrobot.eventHandler.GroupInfoHandler;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.MemberJoinEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 群事件 - 群事件监听器，包括 群设置、群成员 等
 */
@Component
@Slf4j
public class GroupInfoListener {

    @Autowired
    private GroupInfoHandler groupInfoHandler;


    public void onMemberJoin(MemberJoinEvent memberJoinEvent){
        // 主动入群 与 受邀入群
        if(memberJoinEvent instanceof MemberJoinEvent.Active){
            groupInfoHandler.memberJoinActive((MemberJoinEvent.Active) memberJoinEvent);
        } else if(memberJoinEvent instanceof MemberJoinEvent.Invite){
            groupInfoHandler.memberJoinInvite((MemberJoinEvent.Invite) memberJoinEvent);
        }
    }

}
