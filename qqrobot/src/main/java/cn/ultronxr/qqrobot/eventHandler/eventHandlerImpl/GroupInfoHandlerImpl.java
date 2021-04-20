package cn.ultronxr.qqrobot.eventHandler.eventHandlerImpl;

import cn.ultronxr.qqrobot.eventHandler.GroupInfoHandler;
import cn.ultronxr.qqrobot.util.AliOSSUtils;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.event.events.MemberJoinEvent;
import net.mamoe.mirai.event.events.MemberLeaveEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.utils.ExternalResource;
import org.springframework.stereotype.Component;

import java.io.File;


@Component
@Slf4j
public class GroupInfoHandlerImpl implements GroupInfoHandler {

    /** 新成员入群提醒图片路径 **/
    private static final String GROUP_NEW_MEMBER_JOIN_JPG_FILEPATH = "cache" + File.separator + "groupNewMemberJoin.jpg";

    /** 新成员入群提醒图片文件 **/
    private static File GROUP_NEW_MEMBER_JOIN_JPG_FILE;

    static {
        // 如果本地文件不存在，就从OSS获取文件
        if(!AliOSSUtils.getFileObject(
                "workspace/java/robot/qqrobot/GroupInfo/",
                "groupNewMemberJoin.jpg",
                GROUP_NEW_MEMBER_JOIN_JPG_FILEPATH,
                true)){
            log.info("[function] 新成员入群提醒图片文件不存在！");
        } else {
            GROUP_NEW_MEMBER_JOIN_JPG_FILE = new File(GROUP_NEW_MEMBER_JOIN_JPG_FILEPATH);
            log.info("[function] 新成员入群提醒图片文件路径：{}", GROUP_NEW_MEMBER_JOIN_JPG_FILEPATH);
        }
    }


    @Override
    public void memberJoinInvite(MemberJoinEvent.Invite memberJoinInviteEvent) {
        Member member = memberJoinInviteEvent.getMember(),
                invitor = memberJoinInviteEvent.getInvitor();
        log.info("[function] 新成员受邀请入群事件。新成员信息：QQ {} ，昵称 {} ；邀请人信息：QQ {} ，昵称 {}",
                member.getId(), member.getNick(), invitor.getId(), invitor.getNick());

        defaultMemberJoinAction(memberJoinInviteEvent, member.getId());
    }

    @Override
    public void memberJoinActive(MemberJoinEvent.Active memberJoinActiveEvent) {
        Member member = memberJoinActiveEvent.getMember();
        log.info("[function] 新成员主动入群事件。新成员信息：QQ {} ，昵称 {}", member.getId(), member.getNick());

        defaultMemberJoinAction(memberJoinActiveEvent, member.getId());
    }

    /**
     * 默认情况下新成员入群都调用这个方法
     * 如有其它需要，自行修改上面的方法 memberJoinXxx() 方法
     *
     * @param memberJoinEvent 新成员入群事件
     * @param newMemberId     新成员QQ号
     */
    private void defaultMemberJoinAction(MemberJoinEvent memberJoinEvent, Long newMemberId){
        Image image = null;
        if(null != GROUP_NEW_MEMBER_JOIN_JPG_FILE){
            image = memberJoinEvent.getGroup().uploadImage(ExternalResource.create(GROUP_NEW_MEMBER_JOIN_JPG_FILE));
        }
        MessageChainBuilder msgChainBuilder = new MessageChainBuilder()
                .append(new At(newMemberId))
                .append(" 欢迎新成员入群，请先阅读群内公告。");
        if(null != image){
            msgChainBuilder.append(image);
        }
        MessageChain msgChain = msgChainBuilder.build();

        memberJoinEvent.getGroup().sendMessage(msgChain);
        log.info("[message-send] {}", msgChain.contentToString());
    }

    @Override
    public void memberLeaveKick(MemberLeaveEvent.Kick memberLeaveKickEvent) {
        Member member = memberLeaveKickEvent.getMember(),
                operator = memberLeaveKickEvent.getOperator();
        String leaveInfo = "退群成员信息：QQ " + member.getId() + " ，昵称 " + member.getNick() + " ；操作人信息：QQ "
                + operator.getId() + " ，昵称 " + operator.getNick();
        String msg = "群成员被踢：" + leaveInfo;
        log.info("[function] 群成员被踢退群事件。" + leaveInfo);

        memberLeaveKickEvent.getGroup().sendMessage(msg);
        log.info("[message-send] {}", msg);
    }

    @Override
    public void memberLeaveQuit(MemberLeaveEvent.Quit memberLeaveQuitEvent) {
        Member member = memberLeaveQuitEvent.getMember();
        String leaveInfo = "退群成员信息：QQ " + member.getId() + " ，昵称 " + member.getNick();
        String msg = "群成员主动退群：" + leaveInfo;
        log.info("[function] 群成员主动退群事件。" + leaveInfo);

        memberLeaveQuitEvent.getGroup().sendMessage(msg);
        log.info("[message-send] {}", msg);
    }

}