package cn.ultronxr.qqrobot.eventHandler.eventHandlerImpl;

import cn.ultronxr.qqrobot.bean.GlobalData;
import cn.ultronxr.qqrobot.eventHandler.MsgImgHandler;
import cn.ultronxr.qqrobot.service.ImgService;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.utils.ExternalResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;


@Component
@Slf4j
public class MsgImgHandlerImpl extends GlobalData implements MsgImgHandler {

    @Autowired
    private ImgService imgService;


    @Override
    public void groupImgPornHubIconHandler(GroupMessageEvent groupMsgEvent, String msgCode, String msgContent, String msgPlain) {
        msgPlain = msgPlain.replaceFirst("phub", "").strip();
        String[] texts = msgPlain.split(" +");
        if(texts.length < 2){
            String msg = "调用格式：\nphub prefixText suffixText\n（phub 前缀文字 后缀文字）";
            groupMsgEvent.getSubject().sendMessage(msg);
            log.info(msg);
            return;
        }
        try {
            InputStream inputStream = imgService.createPornHubIconImgInputStream(texts[0], texts[1]);
            Image image = groupMsgEvent.getSubject().uploadImage(ExternalResource.create(inputStream));
            inputStream.close();
            groupMsgEvent.getSubject().sendMessage(image);
            log.info("[msg-send] 发送PornHub图标样式图片：prefix suffix - {} {}", texts[0], texts[1]);
        } catch (IOException ex) {
            ex.printStackTrace();
            log.warn("[function] 发送PornHub图标样式图片失败：prefix suffix - {} {}", texts[0], texts[1]);
        }
    }

}
