package cn.ultronxr.qqrobot.eventHandler.eventHandlerImpl;

import cn.ultronxr.qqrobot.bean.mybatis.bean.QQGroupMember;
import cn.ultronxr.qqrobot.eventHandler.MsgGroupChatStatisticsHandler;
import cn.ultronxr.qqrobot.service.GroupChatStatisticsService;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;

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

    @Override
    public void statisticsAllGroup(GroupMessageEvent groupMsgEvent, String msgCode, String msgContent, String msgPlain) {
        msgPlain = msgPlain.replaceAll("所有群活跃", "").strip();
        Calendar calendar = Calendar.getInstance();
        String regex = "(上|最近)(\\d+)(天|周|月|年)";
        // TODO 2021-5-6 23:30:34 把这里和下面的方法写完

    }

    @Override
    public void statisticsGroupAllMember(GroupMessageEvent groupMsgEvent, String msgCode, String msgContent, String msgPlain) {
        msgPlain = msgPlain.replaceAll("群活跃|水群排行|水群排名", "").strip();
        Calendar calendar = Calendar.getInstance();

    }

}
