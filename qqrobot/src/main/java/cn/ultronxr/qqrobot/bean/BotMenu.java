package cn.ultronxr.qqrobot.bean;

import cn.ultronxr.qqrobot.eventHandler.MsgWeatherHandler;
import cn.ultronxr.qqrobot.eventHandler.eventHandlerImpl.MsgWeatherHandlerImpl;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ultronxr
 * @date 2021/05/07 18:51
 *
 * BOT功能菜单
 */
@Data
@NoArgsConstructor
@Component
@Slf4j
public class BotMenu {

    private static ArrayList<BotCmd> botCmdList = new ArrayList<>();

    @Autowired
    private MsgWeatherHandler msgWeatherHandler;


    public void initBotMenu() {
        botCmdList.add(initBotCmdWeather());
    }

    @NotNull
    private ArrayList<Options> defaultOptionsList() {
        ArrayList<Options> optionsList = new ArrayList<>(5);
        // 第一个 无参 参数组
        optionsList.add(new Options());
        // 第二个 -h 参数组
        Options optionsHelp = new Options();
        Option optionHelp = new Option("h", "help", false, "命令解释说明");
        optionHelp.setRequired(true);
        optionsHelp.addOption(optionHelp);
        optionsList.add(optionsHelp);
        return optionsList;
    }

    private BotCmd initBotCmdWeather() {
        BotCmd botCmd = new BotCmd();
        botCmd.setBriefDesc("天气信息。");
        botCmd.setDetailedDesc("获取指定地区的天气信息；缺省地区为杭州。");
        botCmd.setTriggerList(new ArrayList<>(){{ add("天气"); }});

        ArrayList<Options> optionsList = defaultOptionsList();
        Options options3 = new Options();
        Option option3 = new Option("a", "area", true, "地区");
        option3.setRequired(true);
        options3.addOption(option3);
        optionsList.add(options3);
        botCmd.setOptionsList(optionsList);

        botCmd.setHandler(msgWeatherHandler);

        List<Method> methodList = new ArrayList<>();
        try {
            // 这里处理三种参数组的方法都是同一个
            Method method = MsgWeatherHandlerImpl.class.getDeclaredMethod("groupWeatherHandler", GroupMessageEvent.class, String.class, Options.class);
            methodList.add(method);
            methodList.add(method);
            methodList.add(method);
        } catch (NoSuchMethodException ex) {
            log.warn("[botCmd] method.getDeclaredMethod添加方法List抛出异常！");
            ex.printStackTrace();
        }
        botCmd.setHandlerMethodList(methodList);
        return botCmd;
    }


    /**
     * 返回文字版的功能命令菜单
     */
    public String getTextMenu() {
        StringBuilder strBuilder = new StringBuilder();
        botCmdList.forEach(cmd -> strBuilder.append(cmd.getBriefDescription()));
        return strBuilder.toString();
    }

    /**
     * 检查一条纯文本消息语句触发了哪条BOT功能命令 <br/>
     * 检查方法为消息语句是否以命令关键词开头<br/>
     * 如果命令关键词设置不当，可能会导致一条消息匹配到多个功能命令，
     * 所以这个方法总是只返回第一个（从下标0开始递增查找）匹配的功能命令
     *
     * @param msgPlain 纯文本消息语句
     * @return {@code BotCmd} 触发的BOT功能命令对象，如果没有匹配结果返回null
     */
    public BotCmd checkBotCmd(String msgPlain) {
        if(StringUtils.isEmpty(msgPlain)) {
            return null;
        }
        msgPlain = msgPlain.strip();
        // 数据量不大直接嵌套for
        for(BotCmd cmd : botCmdList) {
            for(String trigger : cmd.getTriggerList()) {
                if(msgPlain.startsWith(trigger + " ")) {
                    return cmd;
                }
            }
        }
        return null;
    }

}