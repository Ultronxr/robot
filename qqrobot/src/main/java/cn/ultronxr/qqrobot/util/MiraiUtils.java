package cn.ultronxr.qqrobot.util;

import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.GroupMessageEvent;

/**
 * 处理与Mirai有关的功能的工具类库
 */
@Slf4j
public class MiraiUtils {

    /**
     * 以下是自定义的几种处理Mirai消息的方法，使用前请仔细阅读如下内容。
     *
     * Mirai处理QQ消息时，会产生如下两种消息语句（这里举查询天气的例子）：
     *   1.     [mirai:source:111,-222222] [mirai:at:333333,@BOT] 杭州天气
     *   2.     @BOT 杭州天气
     *   第1条，是经过Mirai内部处理和标识后的消息语句，中括号[]所括内容，即为 “Mirai标签” ；标签后的内容，是 “消息主体”
     *   第2条，是QQ聊天框中肉眼见到的消息语句，包含如@BOT等 “操作符”
     *
     * 为了便于编程处理，这里命名了三种消息类别，分别是labeledMsg、unlabeledMsg、plainMsg
     * 第一种（labeledMsg）  ：上述的第1条语句（包含Mirai标签和消息主体，不包含操作符）
     * 第二种（unlabeledMsg）：上述的第2条语句（包含操作符和消息主体，不包含Mirai标签）
     * 第三种（plainMsg）    ：上述的第1条语句中，删去Mirai标签的所留部分（*这是最干净的消息语句，不含任何Mirai标签和操作符，只留消息主体）
     *
     *   注意： plainMsg 由 labeledMsg 得到，注意与 unlabeledMsg 的区别
     */
    public static String getLabeledMsg(GroupMessageEvent event){
        return event.getMessage().toString().trim();
    }

    public static String getUnlabeledMsg(GroupMessageEvent event){
        return event.getMessage().contentToString().trim();
    }

    public static String getPlainMsg(String labeledMsg){
        //找到最后一个Mirai标签
        String plainMsg = labeledMsg.substring(labeledMsg.lastIndexOf("[mirai:"));
        int lastIndexOfLabelEnd = plainMsg.indexOf("]");
        plainMsg = plainMsg.substring(lastIndexOfLabelEnd + 1).trim();
        return plainMsg;
    }

    public static String getPlainMsg(GroupMessageEvent event){
        return getPlainMsg(getLabeledMsg(event));
    }

}
