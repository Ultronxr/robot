package cn.ultronxr.qqrobot.eventHandler.eventHandlerImpl;

import cn.ultronxr.qqrobot.bean.BotCmd;
import cn.ultronxr.qqrobot.bean.GlobalData;
import cn.ultronxr.qqrobot.eventHandler.MsgImgHandler;
import cn.ultronxr.qqrobot.service.ImgService;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.utils.ExternalResource;
import org.apache.commons.cli.CommandLine;
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
    public void groupImgPornHubIconHandler(BotCmd botCmd, Integer idx, CommandLine cmdLine, GroupMessageEvent groupMsgEvent, String msgPlain) {
        String prefix = "", suffix = "";
        if(cmdLine.getOptions().length == 0) {
            String[] texts = msgPlain.replaceFirst("phub", "").strip().split(" +");
            if(texts.length != 2) {
                groupImgPornHubIconHelper(botCmd, idx, cmdLine, groupMsgEvent, msgPlain);
                return;
            }
            prefix = texts[0];
            suffix = texts[1];
        } else if(cmdLine.hasOption("p") && cmdLine.hasOption("s")) {
            prefix = cmdLine.getOptionValue("p");
            suffix = cmdLine.getOptionValue("s");
        }
        try {
            InputStream inputStream = imgService.createPornHubIconImgInputStream(prefix, suffix);
            Image image = groupMsgEvent.getSubject().uploadImage(ExternalResource.create(inputStream));
            inputStream.close();
            groupMsgEvent.getSubject().sendMessage(image);
            log.info("[Msg-Send] 发送PornHub图标样式图片：prefix suffix - {} {}", prefix, suffix);
        } catch (IOException ex) {
            ex.printStackTrace();
            log.warn("[Function] 发送PornHub图标样式图片失败：prefix suffix - {} {}", prefix, suffix);
        }
    }

    @Override
    public void groupImgPornHubIconHelper(BotCmd botCmd, Integer idx, CommandLine cmdLine, GroupMessageEvent groupMsgEvent, String msgPlain) {
        String helper = botCmd.getDescription();
        helper = helper.replaceFirst("usage: 关键词", "usage: 关键词 <前缀> <后缀>");
        groupMsgEvent.getSubject().sendMessage(helper);
        log.info("[Msg-Send] {}", helper);
    }

}
