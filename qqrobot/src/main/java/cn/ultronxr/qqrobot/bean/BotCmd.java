package cn.ultronxr.qqrobot.bean;

import cn.ultronxr.qqrobot.util.CommonCliUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.Options;
import org.jetbrains.annotations.NotNull;
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
     * 功能命令触发关键词List（功能命令名称）<br/>
     * 当消息文本内容中检测到这些关键词中的任意一个（一般是以某个关键词开头且后跟空格），即进入这个功能命令的处理流程
     */
    private List<String> triggerList;

    /**
     * 功能命令参数组List，这条命令可以携带的每一种（组）命令参数<br/>
     * List中的每一个 {@link Options} 都代表一组可行的参数组合，每一个Options都需要单独设置
     */
    private List<Options> optionsList;

    /**
     * 处理功能命令的对象（被用于反射获取 {@link Method#invoke(Object, Object...)} ）<br/>
     * 下面 {@link #handlerMethodList} 中的所有处理方法都被包含在这个handler对象里面
     */
    private Object handler;

    /**
     * 处理功能命令的方法List，
     * 这里的所有处理方法都被包含在 {@link #handler} 对象里面，使用 {@link Class#getDeclaredMethod(String, Class[])} 方法获取<br/>
     *
     * {@link #optionsList} 中的每一个命令参数组 options 都对应这里的一个处理方法；
     * （有几个参数组就需要填入几个对应的处理方法，如果几个参数组都对应同一个处理方法，那就在这几个下标位置插入同一个method。）<br/>
     * 例如：当index=0，命令参数组是 optionsList.get(0)，处理这条命令和这些参数的方法为 handlerMethodList.get(0)
     */
    private List<Method> handlerMethodList;


    @NotNull
    private StringBuilder getDescription(String desc) {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("关键词：");
        triggerList.forEach(trigger -> strBuilder.append(trigger).append(" "));
        strBuilder.append("\n").append(desc).append("\n");
        return strBuilder;
    }

    /**
     * 获取这条功能命令的简介解释说明，
     * 包括功能命令关键词、简介
     *
     * @return 命令关键词、简介
     */
    public String getBriefDescription() {
        return getDescription(briefDesc).toString();
    }

    /**
     * 获取这条功能命令的详细解释说明，
     * 包括功能命令关键词、详细解释说明、命令参数说明
     *
     * @return 命令关键词、详细解释说明、命令参数说明
     */
    public String getDetailedDescription() {
        StringBuilder strBuilder = getDescription(detailedDesc);
        optionsList.forEach(opts -> strBuilder.append(CommonCliUtils.describeOptions(opts)));
        strBuilder.append("\n");
        return strBuilder.toString();
    }

}
