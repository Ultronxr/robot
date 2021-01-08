package cn.ultronxr.reminder.bean;

import lombok.Data;

import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;


@Data
public abstract class GlobalData {

    /** ResourceBundle封装 */
    @Data
    public static class ResourceBundles {

        public static final ResourceBundle WATER_POWER_REMINDER = ResourceBundle.getBundle("waterAndPowerReminderConfig");

        public static final ResourceBundle TENCENT_CLOUD = ResourceBundle.getBundle("tencentCloudConfig");

        /**
         * 获取.properties资源文件中的（简体）中文内容时，进行转码以解决中文乱码问题
         * 传入的键不含中文，返回的值包含中文
         *
         * @param key 键
         *
         * @return 返回传入键所对应的{@code String}值
         */
        public static String getChineseResource(String key) {
            return new String(WATER_POWER_REMINDER.getString(key).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        }
    }


    /** 给停水/停电HTTP Server使用的重定向URL */
    @Data
    public static class WaterAndPowerNewsUrl {

        public static String WATER_NEWS_URL;

        public static String WATER_NEWS_MOBILE_URL;

        public static String POWER_NEWS_URL;

        public static String POWER_NEWS_MOBILE_URL;
    }

}
