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

import java.util.*;
import java.util.regex.Pattern;


@Slf4j
public class MainMethodTest {

    public static void main(String[] args) {

        //HashMap<String, Object> paramMap = new HashMap<>();
        //paramMap.put("userid", "20497889");
        //paramMap.put("offset", "0");
        //paramMap.put("limit", "10");
        //paramMap.put("heybox_id", "-1");
        //paramMap.put("imei", "a0fb03d762f840b5");
        //paramMap.put("os_type", "Android");
        //paramMap.put("os_version", "6.0.1");
        //
        ////paramMap.put("version", "1.3.159");
        //paramMap.put("_time", "1619840533");
        //String _time = StringUtils.cutOffStringOverLength(String.valueOf(Calendar.getInstance().getTimeInMillis()), 10);
        //System.out.println(_time);
        ////paramMap.put("_time", _time);
        ////paramMap.put("hkey", "K8MD878");
        //paramMap.put("channel", "heybox HTTP/1.1");
        //
        //String USER_AGENT = "Mozilla/5.0 AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.118 Safari/537.36 ApiMaxJia/1.0";
        //
        //String response = HttpRequest.get("https://api.xiaoheihe.cn/bbs/app/profile/user/link/list")
        //        .header("User-Agent", USER_AGENT)
        //        .header("Accept-Encoding", "gzip")
        //        .header("Accept", "*/*")
        //        .header("Referer", "http://api.maxjia.com/")
        //        .header("Host", "api.xiaoheihe.cn")
        //        .form(paramMap)
        //        .execute()
        //        .body();
        //
        //System.out.println(UnicodeUtil.toString(response));

        //后天上午9点开会，给我定时8点半的闹钟
        //
        //List<TimeNLP> list = TimeNLPUtil.parse("七天前");
        //list.forEach(l ->
        //    log.info("{}", l.getTimeNormFormat())
        //);

        //String msgPlain = "群活跃 上周";
        //msgPlain = msgPlain.replaceAll("群活跃|水群排行|水群排名", "").strip();
        //System.out.println(msgPlain);


        //System.out.println(DateTimeUtils.getFormattedCalendar(DateTimeUtils.calculateDay(null, -0), null));
        //System.out.println(DateTimeUtils.getFormattedCalendar(DateTimeUtils.calculateWeek(null, -3), null));
        //System.out.println(DateTimeUtils.getFormattedCalendar(DateTimeUtils.calculateWeekAndDayOfWeek(null, -1, Calendar.SUNDAY), null));
        //System.out.println(DateTimeUtils.getFormattedCalendar(DateTimeUtils.calculateMonth(null, -1), null));
        //System.out.println(DateTimeUtils.getFormattedCalendar(DateTimeUtils.calculateMonthAndDayOfMonth(null, -1, 0), null));


        String cmd = "所有群活跃 -last 上7天";
        String cmd2 = "所有群活跃 -s 2021-05-01-00 -e 2021-05-31-24";

        //Options options = CommonCli.GroupChatStatistics.allGroup;

        //CommandLine cli = null;
        //
        //try {
        //    cli = cliParser.parse(options, cmd.split(" "));
        //
        //    if(cli.hasOption("l")){
        //        log.info("{}", cli.getOptionValue("l"));
        //    }
        //    if(cli.hasOption("s")){
        //        log.info("{}", cli.getOptionValue("s"));
        //    }
        //    if(cli.hasOption("e")){
        //        log.info("{}", cli.getOptionValue("e"));
        //    }
        //} catch (ParseException e) {
        //    e.printStackTrace();
        //}
        //
        //System.out.println(CommonCliUtils.describeOptions(options));

        //String regex = "(上|最近)(\\d+)(天|周|月|年)";
        //List<String> list = ReUtil.getAllGroups(Pattern.compile(regex), "上1天");
        //log.info("{}", list);

    }

}
