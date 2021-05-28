package cn.ultronxr.qqrobot.eventHandler.eventHandlerImpl;

import cn.ultronxr.qqrobot.bean.BotCmd;
import cn.ultronxr.qqrobot.bean.GlobalData;
import cn.ultronxr.qqrobot.eventHandler.MsgImgHandler;
import cn.ultronxr.qqrobot.service.ImgService;
import cn.ultronxr.qqrobot.util.CommonCliUtils;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.utils.ExternalResource;
import org.apache.commons.cli.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Ultronxr
 * @date 2020/12/31 15:03
 */
@Component
@Slf4j
public class MsgImgHandlerImpl extends GlobalData implements MsgImgHandler {

    @Autowired
    private ImgService imgService;


    @Override
    public void groupImgPornHubIconHandler(BotCmd botCmd, CommandLine cmdLine, GroupMessageEvent groupMsgEvent, String msgPlain) {
        if(cmdLine.hasOption("help")) {
            // 优先判断 --help 选项
            CommonCliUtils.defaultOptionHelper(groupMsgEvent, botCmd);
            return;
        }

        String prefix = null, suffix = null;
        if(cmdLine.getOptions().length == 0) {
            // 未指定参数选项的情况，直接取arg1、arg2分别作为-p、-s选项参数（如：“phub str1 str2” 直接取“str1”和“str2”）
            if(cmdLine.getArgList().size() != 3) {
                CommonCliUtils.defaultOptionExceptionHandler(groupMsgEvent);
                return;
            }
            prefix = cmdLine.getArgList().get(1);
            suffix = cmdLine.getArgList().get(2);
        } else if(cmdLine.hasOption("p") && cmdLine.hasOption("s")) {
            // 指定 -p -s 选项的情况
            prefix = cmdLine.getOptionValue("p");
            suffix = cmdLine.getOptionValue("s");
        } else {
            // 其他情况
            CommonCliUtils.defaultOptionExceptionHandler(groupMsgEvent);
            return;
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

}
