package cn.ultronxr.qqrobot.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.jetbrains.annotations.NotNull;

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
    public static final CommandLineParser CLI_PARSER = new DefaultParser();

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
        return "空参数组";
    }

}
