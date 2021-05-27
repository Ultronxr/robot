package cn.ultronxr.qqrobot;

import cn.hutool.core.date.CalendarUtil;
import cn.hutool.core.text.UnicodeUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.http.HttpRequest;
import cn.ultronxr.qqrobot.bean.mybatis.bean.QQGroup;
import cn.ultronxr.qqrobot.util.CommonCliUtils;
import cn.ultronxr.qqrobot.util.DateTimeUtils;
import cn.ultronxr.qqrobot.util.StringUtils;
import com.xkzhangsan.time.nlp.TimeNLP;
import com.xkzhangsan.time.nlp.TimeNLPUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.regex.Pattern;


@Slf4j
public class MainMethodTest {

    public static void main(String[] args) {
        //commonCliTest();
        commonCliHelpTest();
    }

    /**
     * CommonCli测试
     */
    public static void commonCliTest() {
        String cmd = "所有群活跃 -last 上7天";
        String cmd2 = "所有群活跃 -s 2021-05-01-00 -e 2021-05-31-24";

        CommandLineParser cliParser = CommonCliUtils.CLI_PARSER;
        CommandLine cli = null;

        Options options = new Options();
        options.addOption(Option.builder("l").longOpt("line").hasArg(true).argName("刷屏行数").type(Integer.class)
                .desc("刷屏行数").required(false).optionalArg(true).build());

        String[] args1 = "clear 10".split(" ");

        try {
            cli = cliParser.parse(options, args1);

            if(cli.hasOption("l")){
                log.info("{}", cli.getOptionValue("l"));
            }
            log.info("{}", Arrays.asList(cli.getArgs()));
        } catch (ParseException ex) {
            if(ex instanceof org.apache.commons.cli.MissingOptionException) {
                log.info("解析失败，缺少参数：{}", ex.getMessage());
            } else if(ex instanceof org.apache.commons.cli.MissingArgumentException) {
                log.info("解析失败，缺少参数值：{}", ex.getMessage());
            }
            ex.printStackTrace();
        }

        System.out.println(CommonCliUtils.describeOptions("clear", options));
    }

    public static void commonCliHelpTest() {
        CommandLineParser cliParser = CommonCliUtils.CLI_PARSER;
        CommandLine cli = null;
        Options options = new Options();
        options.addOption(Option.builder().longOpt("help").hasArg(false)
                .desc("获取命令帮助").required(true).optionalArg(false).build());

        String[] args1 = "clear --help".split(" ");
        try {
            cli = cliParser.parse(options, args1);
            if(cli.hasOption("help")) {
                System.out.println(11);
            }
            if(cli.hasOption("h")) {
                System.out.println(222);
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        System.out.println(CommonCliUtils.describeOptions("clear", options));
    }

    /**
     * 自然语言时间描述解析测试
     */
    public static void timeNLPTest() {
        //后天上午9点开会，给我定时8点半的闹钟
        List<TimeNLP> list = TimeNLPUtil.parse("七天前");
        list.forEach(l ->
            log.info("{}", l.getTimeNormFormat())
        );
    }

    /**
     * DateTimeUtils测试
     */
    public static void dateTimeUtilsTest() {
        System.out.println(DateTimeUtils.getFormattedCalendar(DateTimeUtils.calculateDay(null, -0), null));
        System.out.println(DateTimeUtils.getFormattedCalendar(DateTimeUtils.calculateWeek(null, -3), null));
        System.out.println(DateTimeUtils.getFormattedCalendar(DateTimeUtils.calculateWeekAndDayOfWeek(null, -1, Calendar.SUNDAY), null));
        System.out.println(DateTimeUtils.getFormattedCalendar(DateTimeUtils.calculateMonth(null, -1), null));
        System.out.println(DateTimeUtils.getFormattedCalendar(DateTimeUtils.calculateMonthAndDayOfMonth(null, -1, 0), null));
    }

    /**
     * 小黑盒游戏资讯API测试
     */
    public static void blackBoxAPITest() {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("userid", "20497889");
        paramMap.put("offset", "0");
        paramMap.put("limit", "10");
        paramMap.put("heybox_id", "-1");
        paramMap.put("imei", "a0fb03d762f840b5");
        paramMap.put("os_type", "Android");
        paramMap.put("os_version", "6.0.1");

        //paramMap.put("version", "1.3.159");
        paramMap.put("_time", "1619840533");
        String _time = StringUtils.cutOffStringOverLength(String.valueOf(Calendar.getInstance().getTimeInMillis()), 10);
        System.out.println(_time);
        //paramMap.put("_time", _time);
        //paramMap.put("hkey", "K8MD878");
        paramMap.put("channel", "heybox HTTP/1.1");

        String USER_AGENT = "Mozilla/5.0 AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.118 Safari/537.36 ApiMaxJia/1.0";

        String response = HttpRequest.get("https://api.xiaoheihe.cn/bbs/app/profile/user/link/list")
                .header("User-Agent", USER_AGENT)
                .header("Accept-Encoding", "gzip")
                .header("Accept", "*/*")
                .header("Referer", "http://api.maxjia.com/")
                .header("Host", "api.xiaoheihe.cn")
                .form(paramMap)
                .execute()
                .body();

        System.out.println(UnicodeUtil.toString(response));
    }

}
