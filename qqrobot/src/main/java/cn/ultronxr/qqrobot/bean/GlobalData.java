package cn.ultronxr.qqrobot.bean;

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

    /** 常用正则封装 */
    @Data
    public static class Regex {

        public static final String IP = "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

        public static final String DOMAIN = "^[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)";

        public static final String URL = "^https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)";

    }

}
