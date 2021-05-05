package cn.ultronxr.qqrobot.eventHandler.eventHandlerImpl;

import cn.ultronxr.qqrobot.bean.mybatis.bean.QQGroupMember;
import cn.ultronxr.qqrobot.eventHandler.MsgGroupChatStatisticsHandler;
import cn.ultronxr.qqrobot.service.GroupChatStatisticsService;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Ultronxr
 * @date 2021/05/05 17:20
 */
@Component
@Slf4j
public class MsgGroupChatStatisticsHandlerImpl implements MsgGroupChatStatisticsHandler {

    @Autowired
    private GroupChatStatisticsService statisticsService;


    @Override
    public void groupChatStatisticsHandler(GroupMessageEvent groupMsgEvent) {
        String groupId = String.valueOf(groupMsgEvent.getSubject().getId()),
                memberId = String.valueOf(groupMsgEvent.getSender().getId());
        // 维护QQ群及群成员
        QQGroupMember qqGroupMember = statisticsService.maintainGroupsAndMembers(groupId, memberId);
        // 维护发言记录
        statisticsService.maintainGroupMemberChats(qqGroupMember, 1);
    }

}
