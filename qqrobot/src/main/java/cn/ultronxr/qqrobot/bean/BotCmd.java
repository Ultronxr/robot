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
     * 命令触发关键词List（功能命令名称）<br/>
     * 当消息文本内容中检测到这些关键词中的任意一个（一般是以某个关键词开头且后跟空格），即进入这个功能命令的处理流程
     */
    private List<String> triggerList;

    /**
     * 命令参数组列表List，这条命令可以携带的每一种（组）命令参数<br/>
     * List中的每一个 {@link Options} 都代表一组可行的参数组合，每一个Options都需要单独设置
     */
    private List<Options> optionsList;

    /**
     * 处理功能命令的方法List，上面 {@link #optionsList} 中的每一个命令参数组 options 都对应这里的一个处理方法；<br/>
     *   例：当index=0，命令参数组是 optionsList.get(0)，处理这条命令和参数组的方法为 handlerMethodList.get(0) <br/>
     *   （有几个参数组就需要填入几个对应的处理方法，如果几个参数组都对应同一个处理方法，那就在这几个下标位置插入同一个method。）<br/>
     *
     * 这里的所有处理方法都会被下面的 {@link #handler} 对象调用，这些方法使用 {@link Class#getDeclaredMethod(String, Class[])} 方法获取 <br/>
     */
    private List<Method> handlerMethodList;

    /**
     * 处理命令的对象，即调用上面 {@link #handlerMethodList} 中的所有处理方法的handler <br/>
     * 被用于反射调用处理方法 {@link Method#invoke(Object, Object...)}
     */
    private Object handler;


    /**
     * 获取这条功能命令的简介说明
     * （用于菜单，列出所有功能命令）
     *
     * @return 这条功能命令的简介说明，包含关键词
     */
    public StringBuilder getBriefDescription() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("命令关键词：");
        triggerList.forEach(trigger -> strBuilder.append(trigger).append(" "));
        strBuilder.append("\n");
        return strBuilder;
    }

    /**
     * 获取这条功能命令的所有内容的解释说明
     *
     * @return 这条功能命令的所有内容的解释说明
     */
    public String getDescription() {
        StringBuilder strBuilder = getBriefDescription()
                .append("简介：").append(briefDesc).append("\n")
                .append("详细说明：").append(detailedDesc).append("\n");
        if(optionsList != null) {
            strBuilder.append("参数组列表：\n");
            for(int i = 0; i < optionsList.size(); ++i) {
                strBuilder.append(i+1).append(".\n")
                        //.append(CommonCliUtils.describeOptions(optionsList.get(i)))
                        .append(CommonCliUtils.describeOptions("关键词", optionsList.get(i)))
                        .append("\n");
            }
        }
        return strBuilder.toString();
    }

}
