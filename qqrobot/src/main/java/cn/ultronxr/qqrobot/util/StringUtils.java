package cn.ultronxr.qqrobot.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ultronxr
 * @date 2021/04/30 18:11
 *
 * String操作工具方法库
 */
public class StringUtils {

    /**
     * 截断字符串，保留 从开头至指定长度 的部分
     *
     * @param string 原字符串
     * @param length 保留部分的指定长度
     * @return {@code String} 字符串被截断之后，保留下来的部分
     */
    public static String cutOffStringOverLength(String string, int length) {
        if(null == string || string.isEmpty()
                || length < 0 || length > string.length()){
            return string;
        }
        return string.substring(0, length);
    }


    /**
     * 把字符串转换为 Unicode编码形式
     * 例：汉字abc123.,! -> \u6c49\u5b57\u0061\u0062\u0063\u0031\u0032\u0033\u002e\u002c\u0021
     * @param plainText 待转换的文本
     * @return Unicode编码形式字符串
     */
    public static String native2Unicode(String plainText) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < plainText.length(); i++){
            String hexStr = Integer.toString((int)plainText.charAt(i), 16);
            sb.append("\\u");
            sb.append("0".repeat(Math.max(0, 4 - hexStr.length())));
            sb.append(hexStr);
        }
        return sb.toString();
    }

    /**
     * 把 Unicode编码形式的字符串 转换为 正常可读的文本
     * 例：\u6c49\u5b57\u0061\u0062\u0063\u0031\u0032\u0033\u002e\u002c\u0021 -> 汉字abc123.,!
     * @param unicode Unicode编码形式的字符串
     * @return 正常可读的文本
     */
    public static String unicode2Native(String unicode) {
        Pattern pattern = Pattern.compile("(\\\\u(\\w{4}))");
        Matcher matcher = pattern.matcher(unicode);
        while (matcher.find()) {
            String unicodeFull = matcher.group(1);
            String unicodeNum = matcher.group(2);
            char singleChar = (char) Integer.parseInt(unicodeNum, 16);
            unicode = unicode.replace(unicodeFull, String.valueOf(singleChar));
        }
        return unicode;
    }

}
