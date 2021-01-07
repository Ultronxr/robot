package cn.ultronxr.reminder.bean;

import lombok.Data;

import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;


@Data
public abstract class GlobalData {

    public static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("waterAndPowerReminderConfig");

    /**
     * 获取.properties资源文件中的（简体）中文内容时，进行转码以解决中文乱码问题
     * 传入的键不含中文，返回的值包含中文
     *
     * @param key
     *          键
     *
     * @return
     *          返回传入键所对应的{@code String}值
     */
    public static String getChineseResource(String key) {
        return new String(RESOURCE_BUNDLE.getString(key).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }

}
