package cn.ultronxr.qqrobot.bean;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

/**
 * @author Ultronxr
 * @date 2021/05/07 18:01
 *
 * 使用 {@link org.apache.commons.cli} 解析的命令
 * 这里对命令进行统一管理，后面机器人逻辑代码直接调用就行了
 */
@Slf4j
public class CommonCli {

    /** 命令解析器 */
    public static final CommandLineParser CLI_PARSER = new DefaultParser();

    /** 群/群成员（发言）活跃统计 命令 */
    public static final class GROUP_CHAT_STATISTICS {

        /** <b>所有群活跃<b/> 命令 */
        public static final Options ALL_GROUP;

        /** <b>群活跃|水群排行|水群排名<b/> 命令 */
        public static final Options GROUP_ALL_MEMBER;

        static {
            Option option1 = new Option("l", "last", true, "持续时间"),
                    option2 = new Option("s", "start", true, "开始时间"),
                    option3 = new Option("e", "end", true, "结束时间"),
                    option4 = new Option("h", "help", false, "命令参数的解释说明");
            option1.setRequired(false);
            option2.setRequired(false);
            option3.setRequired(false);
            option4.setRequired(false);

            ALL_GROUP = new Options();
            ALL_GROUP.addOption(option1).addOption(option2).addOption(option3).addOption(option4);
            GROUP_ALL_MEMBER = new Options();
            GROUP_ALL_MEMBER.addOption(option1).addOption(option2).addOption(option3).addOption(option4);

            log.info("[CLI] 命令 {} 初始化完成。", "GroupChatStatistics");
        }
    }


}
