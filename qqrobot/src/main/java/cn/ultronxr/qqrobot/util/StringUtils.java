package cn.ultronxr.qqrobot.util;

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

}
