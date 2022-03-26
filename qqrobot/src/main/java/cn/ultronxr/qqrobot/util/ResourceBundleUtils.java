package cn.ultronxr.qqrobot.util;

import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

/**
 * @author Ultronxr
 * @date 2022/03/11 09:38
 */
public class ResourceBundleUtils {

    /**
     * 获取.properties资源文件中的字符串数组
     * 用途：一个字符串值中包含多个内容，以分割符分割成一个字符串数组
     *
     * @param resourceBundle 从哪个ResourceBundle中读取
     * @param key 键
     * @param split 分割符
     * @return 传入键所对应的字符串数组值
     */
    public static String[] getStrArray(final ResourceBundle resourceBundle, String key, String split) {
        return resourceBundle.getString(key).split(split);
    }

    /**
     * 获取.properties资源文件中的（简体）中文内容时，进行转码以解决中文乱码问题
     * 传入的键不含中文，返回的值包含中文
     *
     * @param resourceBundle 从哪个ResourceBundle中读取
     * @param key 键
     * @return 传入键所对应的{@code String}值
     */
    public static String getChineseStr(final ResourceBundle resourceBundle, String key) {
        return new String(resourceBundle.getString(key).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }

    /**
     * 获取.properties资源文件中的中文字符串数组，进行转码以解决中文乱码问题
     * 传入的键不含中文，返回的值包含中文
     *
     * @param resourceBundle 从哪个ResourceBundle中读取
     * @param key 键
     * @param split 分割符
     * @return 传入键所对应的字符串数组值
     */
    public static String[] getChineseStrArray(final ResourceBundle resourceBundle, String key, String split) {
        return getChineseStr(resourceBundle, key).split(split);
    }

}
