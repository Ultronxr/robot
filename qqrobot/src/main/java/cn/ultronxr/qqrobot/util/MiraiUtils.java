package cn.ultronxr.qqrobot.util;

import cn.ultronxr.qqrobot.bean.GlobalData;
import cn.ultronxr.qqrobot.bean.MiraiCodes;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.At;
import org.jetbrains.annotations.NotNull;


/**
 * 处理与Mirai有关的功能的工具类库
 */
@Slf4j
public class MiraiUtils extends GlobalData {

    /*
     * 以下自定义了三种Mirai消息类别：
     *
     * 1. MsgCode:    带有Mirai码的消息语句，由方法 {@code message.serializeToMiraiCode()} 获得
     * 2. MsgContent: QQ对话框中纯文本方式显示的消息语句，由方法 {@code message.contentToString()} 获得
     * 3. MsgPlain:   纯消息主体内容（最干净的消息类型，不含其它任何内容），由CodesMsg去掉Mirai码得到
     * 4. MsgStr:     包含尽可能多信息的消息语句（<b>行为可能不确定<b/>），由方法 {@code message.toString()} 获得
     *
     * 其中除了第3种是自己编码时新加的，其它1/2/4都是由Mirai原API直接调用获取，关于这几个方法的区别见如下链接：
     * {@link "https://github.com/mamoe/mirai/blob/dev/docs/Messages.md#%E6%B6%88%E6%81%AF%E5%85%83%E7%B4%A0"}
     */

    /**
     * 获取带有Mirai码的消息语句
     * 由方法 {@code message.serializeToMiraiCode()} 获得
     * 详见 {@link MiraiCodes}
     *
     * @param msgEvent        消息事件
     * @return {@code String} 带有Mirai码的消息语句
     */
    public static String getMsgCode(@NotNull MessageEvent msgEvent){
        return msgEvent.getMessage().serializeToMiraiCode();
    }

    /**
     * 获取QQ对话框中纯文本方式显示的消息语句
     * 由方法 {@code message.contentToString()} 获得
     *
     * @param msgEvent        消息事件
     * @return {@code String} QQ对话框中纯文本方式显示的消息语句
     */
    public static String getMsgContent(@NotNull MessageEvent msgEvent){
        return msgEvent.getMessage().contentToString();
    }

    /**
     * 获取纯消息主体内容
     * （最干净的消息类型，不含其它任何内容），由CodesMsg去掉Mirai码得到
     *
     * @param msgEvent        消息事件
     * @return {@code String} 纯消息主体内容
     */
    public static String getMsgPlain(@NotNull MessageEvent msgEvent){
        //使用正则匹配形如[mirai:TYPE:PROP]的Mirai码，并删去
        return getMsgCode(msgEvent).replaceAll("^(\\[mirai:.*])", "").trim();
    }

    /**
     * 获取包含尽可能多信息的消息语句
     * 由方法 {@code message.toString()} 获得
     * <b>行为可能不确定，尽量在主程序中避免使用此方法进行消息处理<b/>
     *
     * @param msgEvent        消息事件
     * @return {@code String} 尽可能多信息的消息语句
     */
    public static String getMsgStr(@NotNull MessageEvent msgEvent){
        return msgEvent.getMessage().toString();
    }



    /**
     * 判断群聊中的某条消息是否@BOT
     *
     * @param groupMsgEvent 群消息事件
     * @return {@code boolean} 布尔值： true - @BOT；false - 没有@BOT
     */
    public static boolean isGroupAtBot(@NotNull GroupMessageEvent groupMsgEvent){
        return groupMsgEvent.getMessage().contains((At) MiraiCodes.AT_BOT.getCodeObj());
    }

}
