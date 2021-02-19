package cn.ultronxr.qqrobot.bean;

import cn.hutool.core.lang.PatternPool;
import lombok.Data;

import java.util.ResourceBundle;


@Data
public abstract class GlobalData {

    /** ResourceBundle封装 */
    @Data
    public static class ResBundle {

        public static final ResourceBundle BOT = ResourceBundle.getBundle("botConfig");

        public static final ResourceBundle ALI_CLOUD = ResourceBundle.getBundle("aliCloudConfig");

    }

    /**
     * 正则表达式
     * 另外可以使用hutool的库 {@link PatternPool}
     */
    @Data
    public static class Regex {

        /** 内网IPv4地址 */
        public static final String IPV4_INTRANET = "^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])$";

        /** 内网网址（不带协议标识符） */
        public static final String URL_INTRANET = "^localhost.*";

    }

    /** 网络请求时使用的User-Agent */
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.81 Safari/537.36";

}
