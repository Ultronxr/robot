package cn.ultronxr.qqrobot.eventHandler.eventHandlerImpl;

import cn.ultronxr.qqrobot.eventHandler.TechNewsHandler;
import cn.ultronxr.qqrobot.service.TechNewsService;
import cn.ultronxr.qqrobot.util.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.utils.ExternalResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Calendar;


@Component
@Slf4j
public class TechNewsHandlerImpl implements TechNewsHandler {

    @Autowired
    private TechNewsService techNewsService;


    @Override
    public ListeningStatus groupQiChaChaNewsHandler(GroupMessageEvent groupMsgEvent, String msgCode, String msgContent, String msgPlain, Integer type) {
        Calendar calendarNow = Calendar.getInstance();
        int[] hours = new int[]{9, 17};
        if(type == 1){
            File imgFile = techNewsService.getQichachaMorningNewsFile();
            if(DateTimeUtils.checkTimeHourPeriod(calendarNow, hours) < 1){
                groupMsgEvent.getSubject().sendMessage("今天的早报未发布哦，先看看昨天的早报吧~");
            }
            // mirai作者推荐使用Contact.sendImage发送图片，但是我在Linux服务器环境下运行报错
            // 所以修改成另一种发送图片的方法，下同
            //Contact.sendImage(groupMsgEvent.getGroup(), imgFile, "png");
            Image image = groupMsgEvent.getSubject().uploadImage(ExternalResource.create(imgFile));
            groupMsgEvent.getSubject().sendMessage(Image.fromId(image.getImageId()));
            log.info("[message-send] [img]企查查早报");
            return ListeningStatus.LISTENING;
        }

        if(DateTimeUtils.checkTimeHourPeriod(calendarNow, hours) < 2){
            groupMsgEvent.getSubject().sendMessage("今天的晚报未发布哦，先看看昨天的晚报吧~");
        }
        File imgFile = techNewsService.getQichachaEveningNewsFile();
        //Contact.uploadImage(groupMsgEvent.getGroup(), imgFile, "png");
        Image image = groupMsgEvent.getSubject().uploadImage(ExternalResource.create(imgFile));
        groupMsgEvent.getSubject().sendMessage(Image.fromId(image.getImageId()));
        log.info("[message-send] [img]企查查晚报");

        return ListeningStatus.LISTENING;
    }

}
