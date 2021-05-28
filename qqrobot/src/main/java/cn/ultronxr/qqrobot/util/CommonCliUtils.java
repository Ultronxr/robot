package cn.ultronxr.qqrobot.util;

import cn.ultronxr.qqrobot.bean.BotCmd;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.apache.commons.cli.*;
import org.jetbrains.annotations.NotNull;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;

/**
 * @author Ultronxr
 * @date 2021/05/07 17:44
 *
 * {@link org.apache.commons.cli} 工具方法库
 */
@Slf4j
public class CommonCliUtils {

    /** 命令解析器 */
    public static final CommandLineParser CLI_PARSER;

    /** 命令参数帮助信息格式化工具 */
    public static final HelpFormatter HELP_FORMATTER;

    private static final StringWriter STRING_WRITER;

    private static final PrintWriter PRINT_WRITER;


    static {
        CLI_PARSER = new DefaultParser();
        HELP_FORMATTER = new HelpFormatter();
        STRING_WRITER = new StringWriter();
        PRINT_WRITER = new PrintWriter(STRING_WRITER);
    }

    /**
     * 列出一个Options中所有参数的解释描述文本
     *
     * @param options {@code Options} 对象
     * @return 所有参数的解释描述文本
     */
    public static String describeOptions(@NotNull Options options) {
        Collection<Option> optCollection = options.getOptions();
        if(optCollection.size() > 0){
            StringBuilder strBuilder = new StringBuilder();
            optCollection.forEach(opt -> {
                strBuilder.append("-").append(opt.getOpt())
                        .append(", --").append(opt.getLongOpt())
                        .append("\n    ").append(opt.getDescription()).append("\n");
            });
            return strBuilder.toString();
        }
        return "空参数组\n";
    }

    /**
     * 使用printHelp库方法规范地打印一个Options中所有参数的解释描述文本
     *
     * @param cmdLineSyntax 命令使用举例中的语法关键词（"usage: xxx"中的"xxx"）
     * @param options       {@code Options} 对象
     * @return 规范化的所有参数的解释描述文本
     */
    public static String describeOptions(@NotNull String cmdLineSyntax, @NotNull Options options) {
        if(options.getOptions().size() == 0) {
            return "usage: 关键词\n 空参数组\n";
        }
        HELP_FORMATTER.printHelp(
                PRINT_WRITER,
                HelpFormatter.DEFAULT_WIDTH,
                cmdLineSyntax,
                null,
                options,
                HelpFormatter.DEFAULT_LEFT_PAD,
                HelpFormatter.DEFAULT_DESC_PAD,
                null,
                true
        );
        PRINT_WRITER.flush();
        String resStr = STRING_WRITER.toString();
        STRING_WRITER.getBuffer().setLength(0);
        return resStr;
    }


    /**
     * 默认的处理 参数格式错误 的方法，
     * 发送一个参数格式错误的提示消息
     *
     * @param groupMsgEvent 群消息事件
     */
    public static void defaultOptionExceptionHandler(@NotNull GroupMessageEvent groupMsgEvent) {
        String msg = "参数格式错误！请尝试使用 --help 选项获取帮助。";
        groupMsgEvent.getSubject().sendMessage(msg);
        log.info("[Msg-Send] {}", msg);
    }

    /**
     * 默认的处理 --help选项 的方法，
     * 发送BotCmd对应的帮助信息
     *
     * @param groupMsgEvent 群消息事件
     * @param botCmd        BotCmd功能命令对象
     */
    public static void defaultOptionHelper(@NotNull GroupMessageEvent groupMsgEvent, @NotNull BotCmd botCmd){
        String helper = botCmd.getDescription(true);
        groupMsgEvent.getSubject().sendMessage(helper);
        log.info("[Msg-Send] {}", helper);
    }

}
