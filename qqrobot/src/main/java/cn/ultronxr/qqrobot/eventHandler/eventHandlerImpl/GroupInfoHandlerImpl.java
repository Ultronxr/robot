package cn.ultronxr.qqrobot.eventHandler.eventHandlerImpl;

import cn.ultronxr.qqrobot.eventHandler.GroupInfoHandler;
import cn.ultronxr.qqrobot.util.AliOSSUtils;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.event.events.MemberJoinEvent;
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

    private static final String GROUP_NEW_MEMBER_JOIN_JPG_FILEPATH = "cache" + File.separator + "groupNewMemberJoin.jpg";

    private static File GROUP_NEW_MEMBER_JOIN_JPG_FILE;

    static {
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
    public void memberJoinActive(MemberJoinEvent.Active memberJoinActiveEvent) {
        Member member = memberJoinActiveEvent.getMember();
        log.info("[function] 新成员主动入群事件。新成员信息：QQ {} ，昵称 {}", member.getId(), member.getNick());

        defaultMemberJoinAction(memberJoinActiveEvent, member.getId());
    }

    @Override
    public void memberJoinInvite(MemberJoinEvent.Invite memberJoinInviteEvent) {
        Member member = memberJoinInviteEvent.getMember(),
               invitor = memberJoinInviteEvent.getInvitor();
        log.info("[function] 新成员受邀请入群事件。新成员信息：QQ {} ，昵称 {} ；邀请人信息：QQ {} ，昵称 {}",
                member.getId(), member.getNick(), invitor.getId(), invitor.getNick());

        defaultMemberJoinAction(memberJoinInviteEvent, member.getId());
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

}
