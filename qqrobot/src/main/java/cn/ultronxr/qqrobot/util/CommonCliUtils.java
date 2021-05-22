package cn.ultronxr.qqrobot.util;

import lombok.extern.slf4j.Slf4j;
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

}
