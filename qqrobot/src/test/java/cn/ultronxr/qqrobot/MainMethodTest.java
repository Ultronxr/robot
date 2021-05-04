package cn.ultronxr.qqrobot;

import cn.hutool.core.text.UnicodeUtil;
import cn.hutool.http.HttpRequest;
import cn.ultronxr.qqrobot.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.HashMap;


@Slf4j
public class MainMethodTest {

    public static void main(String[] args) {

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
