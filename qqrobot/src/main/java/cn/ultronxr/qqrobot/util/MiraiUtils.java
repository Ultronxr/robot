package cn.ultronxr.qqrobot.util;

import cn.hutool.core.util.ReUtil;
import cn.ultronxr.qqrobot.bean.GlobalData;
import cn.ultronxr.qqrobot.bean.MiraiCodes;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.At;
import org.jetbrains.annotations.NotNull;

/**
 * @author Ultronxr
 * @date 2020/12/31 15:03
 *
 * 处理与Mirai有关的功能的工具类库
 */
@Slf4j
public class MiraiUtils extends GlobalData {

    /**
     * Mirai会在消息内容中转义的符号             ,  :  [  ]
     * 上面的符号会在msgCode和msgPlain中显示为  \, \: \[ \]
     * 所以需要把它们去掉反斜杠，转义回来
     */
    public static final String MIRAI_PUNCTUATION = "\\\\[,:\\[\\]]";

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
     * （最干净的消息类型，不含其它任何内容），由CodesMsg去掉Mirai码得到，并去转义
     * @see #MIRAI_PUNCTUATION
     *
     * @param msgEvent        消息事件
     * @return {@code String} 纯消息主体内容
     */
    public static String getMsgPlain(@NotNull MessageEvent msgEvent){
        // 使用正则匹配形如[mirai:TYPE:PROP]的Mirai码，并删去，.*后面加?变成惰性匹配，否则会把消息正文里的]全部删掉
        String msgPlain = getMsgCode(msgEvent).replaceAll("(\\[mirai:.*?])", "").strip();
        // 去转义
        return escapeMiraiPunctuation(msgPlain);
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
     * 把msgPlain中的Mirai转义符号 去转义
     * @see #MIRAI_PUNCTUATION
     *
     * @param msg 去转义之前的消息内容
     * @return {@code String} 去转义之后的消息
     */
    public static String escapeMiraiPunctuation(String msg){
        return ReUtil.replaceAll(msg, MIRAI_PUNCTUATION,
                matcher -> matcher.group().replaceFirst("\\\\", ""));
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
