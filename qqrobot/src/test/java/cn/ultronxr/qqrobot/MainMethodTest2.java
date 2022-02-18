package cn.ultronxr.qqrobot;

import cn.hutool.core.util.ReUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.EncodingUtils;
import org.springframework.web.util.UriUtils;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ultronxr
 * @date 2022/02/18 14:18
 */
public class MainMethodTest2 {

    public static void main(String[] args) {
        //String plainText = "汉字abc123.,!";
        //System.out.println(cn.ultronxr.qqrobot.util.StringUtils.native2Unicode(plainText));
        //
        //String hex = "\\u652f\\u6301\\u6c49\\u5b57\\u8f6c\\u0023\\u0040\\u0046\\u002d\\u0038";
        //System.out.println(cn.ultronxr.qqrobot.util.StringUtils.unicode2Native(hex));
        //
        ////System.out.println(StringUtils.toEncodedString(plainText.getBytes(), StandardCharsets.US_ASCII));
        //System.out.println("\u25903");

        String msg = "\\\\\\,\\:\\[\\]";
        System.out.println(msg);
        String regex = "\\\\[\\\\,:\\[\\]]";
        //Pattern pattern = Pattern.compile(regex);
        //Matcher matcher = pattern.matcher(msg);
        //StringBuilder sb = new StringBuilder();
        //while (matcher.find()) {
        //    matcher.group().replaceFirst("\\\\", "");
        //}
        //System.out.println(sb.toString());

        msg = ReUtil.replaceAll(msg, regex,
                matcher1 -> matcher1.group().replaceFirst("\\\\", "\\\\"));
        System.out.println(msg);

        System.out.println(Matcher.quoteReplacement("\\"));
    }

}
