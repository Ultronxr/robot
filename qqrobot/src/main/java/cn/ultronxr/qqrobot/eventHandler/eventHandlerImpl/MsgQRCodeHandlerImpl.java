package cn.ultronxr.qqrobot.eventHandler.eventHandlerImpl;

import cn.ultronxr.qqrobot.bean.BotCmd;
import cn.ultronxr.qqrobot.eventHandler.MsgQRCodeHandler;
import cn.ultronxr.qqrobot.util.CommonCliUtils;
import cn.ultronxr.qqrobot.util.QRCodeUtils;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.utils.ExternalResource;
import org.apache.commons.cli.CommandLine;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;

/**
 * @author Ultronxr
 * @date 2022/02/17 17:57
 */
@Component
@Slf4j
public class MsgQRCodeHandlerImpl implements MsgQRCodeHandler {


    @Override
    public void groupQRCodeHandler(BotCmd botCmd, CommandLine cmdLine, GroupMessageEvent groupMsgEvent, String msgPlain) {
        if(cmdLine.hasOption("help")) {
            // 优先判断 --help 选项
            CommonCliUtils.defaultOptionHelper(groupMsgEvent, botCmd);
            return;
        }

        String msgRes = "";
        String content = null;
        // 无任何选项时
        if(cmdLine.getOptions().length == 0 && cmdLine.getArgList().size() > 1) {
            // 如果无选项但带了arg1参数，如 ">qr 待转换成二维码的文本内容"
            content = cmdLine.getArgList().get(1);
        } else if(cmdLine.hasOption("e")) {
            content = cmdLine.getOptionValue("e");
        } else if(cmdLine.hasOption("d")) {
            msgRes = handleDecode(groupMsgEvent);
        } else {
            CommonCliUtils.defaultOptionExceptionHandler(groupMsgEvent);
            return;
        }

        MessageChainBuilder msgChainBuilder = new MessageChainBuilder();
        if(content != null) {
            Image image = handleEncode(content, groupMsgEvent);
            if(null != image){
                msgChainBuilder.append(image);
            }
        }

        msgChainBuilder.append(msgRes);
        MessageChain msgChain = msgChainBuilder.build();

        groupMsgEvent.getSubject().sendMessage(msgChain);
        log.info("[Msg-Send] {}", msgChain.contentToString());
    }

    /**
     * 生成二维码图片并发送
     */
    private Image handleEncode(String content, GroupMessageEvent groupMsgEvent) {
        Image image = null;
        // 默认二维码图片大小200px*200px
        int size = 200;
        if(content.length() > 90) {
            size = 600;
        } else if(content.length() > 45) {
            size = 400;
        }
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(
                    QRCodeUtils.encoderToStream(content, "png", size, size).toByteArray());
            ExternalResource externalResource = ExternalResource.create(inputStream);
            image = groupMsgEvent.getGroup().uploadImage(externalResource);
            inputStream.close();
            externalResource.close();
        } catch (IOException | WriterException ex) {
            ex.printStackTrace();
            log.error("[function] 二维码图片操作失败！");
        }
        return image;
    }

    /**
     * 解析二维码图片，并发送解析结果
     */
    private String handleDecode(GroupMessageEvent groupMsgEvent) {
        String res = "";
        MessageChain msgChain = groupMsgEvent.getMessage();
        for(Message msg : msgChain) {
            if(msg instanceof Image) {
                Image img = Image.fromId(((Image) msg).getImageId());
                String url = Image.queryUrl(img);
                log.info("[function] 解析二维码图片：图片ID - {} ； 图片URL - {}", img.getImageId(), url);
                try {
                    BufferedImage bufferedImage = ImageIO.read(new URL(url));
                    res = QRCodeUtils.decoderByBufferedImage(bufferedImage);
                    break;
                } catch (IOException ex) {
                    ex.printStackTrace();
                    res = "解析二维码图片时IO出错！";
                    log.error("[function] {}", res);
                } catch (NotFoundException ex) {
                    ex.printStackTrace();
                    res = "图像中未发现/无法解析二维码！";
                    log.warn("[function] {}", res);
                }
            }
        }
        if(res.length() == 0) {
            res = "请在同一条消息内发送指令和二维码图片。";
        }
        return res;
    }

}
