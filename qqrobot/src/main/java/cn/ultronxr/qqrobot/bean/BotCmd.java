package cn.ultronxr.qqrobot.bean;

import cn.ultronxr.qqrobot.util.CommonCliUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.Options;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Ultronxr
 * @date 2021/05/07 18:25
 *
 * BOT功能菜单的 一条功能命令封装bean
 */
@Data
@NoArgsConstructor
@Component
@Slf4j
public class BotCmd {

    /** 这条功能命令的简介 */
    private String briefDesc;

    /** 这条功能命令的详细解释说明 */
    private String detailedDesc;

    /**
     * 命令触发关键词List（功能命令名称）<br/>
     * 当消息文本内容中检测到这些关键词中的任意一个（一般是以某个关键词开头且后跟空格），即进入这个功能命令的处理流程
     */
    private List<String> triggerList;

    /**
     * 命令参数组，这条命令可以携带的所有命令参数命令参数
     */
    private Options options;

    /**
     * 处理命令的对象，即调用下面的 {@link #handlerMethod} 中的处理方法的handler <br/>
     * 被用于反射调用处理方法 {@link Method#invoke(Object, Object...)}
     */
    private Object handler;

    /**
     * 处理功能命令的方法<br/>
     * 这里的方法会被上面的 {@link #handler} 对象调用，使用 {@link Class#getDeclaredMethod(String, Class[])} 方法获取
     */
    private Method handlerMethod;


    /**
     * 获取这条功能命令的 关键词
     */
    public StringBuilder getTriggerListString(boolean hasLabel) {
        StringBuilder strBuilder = new StringBuilder();
        if(hasLabel) {
            strBuilder.append("○关键词：");
        }
        triggerList.forEach(trigger -> strBuilder.append(trigger).append(" "));
        strBuilder.append("\n");
        return strBuilder;
    }

    /**
     * 获取这条功能命令的 关键词、简介
     */
    public StringBuilder getBriefDescription(boolean hasLabel) {
        StringBuilder strBuilder = getTriggerListString(hasLabel);
        if(hasLabel) {
            strBuilder.append("○简介：");
        }
        strBuilder.append(briefDesc).append("\n");
        return strBuilder;
    }

    /**
     * 获取这条功能命令的所有内容的解释说明
     */
    public String getDescription(boolean hasLabel) {
        StringBuilder strBuilder = getBriefDescription(hasLabel);
        if(hasLabel) {
            strBuilder.append("○详细说明：");
        }
        strBuilder.append(detailedDesc).append("\n");
        if(options != null) {
            if(hasLabel) {
                strBuilder.append("○命令参数：\n");
            }
            strBuilder.append(CommonCliUtils.describeOptions("关键词", options)).append("\n");
        }
        return strBuilder.toString();
    }

}
