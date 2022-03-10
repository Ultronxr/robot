package cn.ultronxr.qqrobot.config;

import cn.ultronxr.qqrobot.bean.BotCmd;
import cn.ultronxr.qqrobot.bean.BotMenu;
import cn.ultronxr.qqrobot.eventHandler.*;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;
import org.yaml.snakeyaml.Yaml;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Ultronxr
 * @date 2021/05/10 18:44
 *
 * 解析功能命令BotCmd配置文件，初始化功能命令菜单BotMenu
 */
@Configuration
@Slf4j
public class BotMenuConfig {

    /** botCmd/botMenu配置文件路径，以 resources包 为根目录 */
    public static final String CONFIG_YAML_PATH = "/conf/botMenu.yaml";

    @Autowired
    private MsgGroupChatStatisticsHandler msgGroupChatStatisticsHandler;

    @Autowired
    private MsgImgHandler msgImgHandler;

    @Autowired
    private MsgMagnetHandler msgMagnetHandler;

    @Autowired
    private MsgRandomHandler msgRandomHandler;

    @Autowired
    private MsgRobotMenuHandler msgRobotMenuHandler;

    @Autowired
    private MsgScheduledTaskHandler msgScheduledTaskHandler;

    @Autowired
    private MsgSentenceHandler msgSentenceHandler;

    @Autowired
    private MsgShellCmdHandler msgShellCmdHandler;

    @Autowired
    private MsgWeatherHandler msgWeatherHandler;

    @Autowired
    private MsgClearHandler msgClearHandler;

    @Autowired
    private MsgQRCodeHandler msgQRCodeHandler;

    @Autowired
    private MsgCodeToolkitHandler msgCodeToolkitHandler;

    @Autowired
    private MsgQuartzTaskHandler msgQuartzTaskHandler;


    /**
     * 初始化BOT命令菜单BotMenu
     * 其他地方调用命令菜单也直接用static方法即可
     */
    @Bean
    public void initBotMenu() {
        try {
            List<BotCmd> botCmdList = processYaml();
            BotMenu.BOT_MENU = (ArrayList<BotCmd>) botCmdList;
        } catch (Exception ex) {
            log.error("[BotMenu] botMenu.yaml解析异常！初始化BotMenu失败！");
            ex.printStackTrace();
            return;
        }
        log.info("[BotMenu] 初始化BotMenu完成。");
    }

    /**
     * 解析.yaml配置文件，组装形成包含所有功能命令BotCmd的列表List
     * 注：
     *   方法中有 "1)" "2)" ... 序号标注的是组装BotCmd的关键步骤
     *
     * @return List<BotCmd> 一个List，其中包含所有功能命令BotCmd
     */
    @SuppressWarnings("unchecked")
    public List<BotCmd> processYaml() {
        Yaml yaml = new Yaml();
        Iterable<Object> yamlAll = yaml.loadAll(BotMenuConfig.class.getResourceAsStream(CONFIG_YAML_PATH));
        List<BotCmd> botCmdList = new ArrayList<>();

        // 遍历yaml解析出来的对象
        while (yamlAll.iterator().hasNext()) {
            LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) yamlAll.iterator().next();

            // 1) 解析并设置命令简介、命令详解、触发关键词
            BotCmd botCmd = new BotCmd();
            botCmd.setBriefDesc(map.get("briefDesc").toString());
            botCmd.setDetailedDesc(map.get("detailedDesc").toString());
            botCmd.setTriggerList((List<String>) map.get("triggerList"));

            // 2) 解析并设置功能命令参数组options
            List<LinkedHashMap<String, Object>> optionsYaml = (List<LinkedHashMap<String, Object>>) map.get("options");
            Options options = new Options();
            // 遍历功能命令参数组options中的参数option（参数内的opt、desc、hasArg等含义请另参见Option的用法）
            optionsYaml.forEach(optionYaml -> {
                // opt可以为null，为null时使用下面的longOpt作为选项
                String opt = optionYaml.get("opt") == null ? null : optionYaml.get("opt").toString();
                String desc = optionYaml.get("desc").toString();
                boolean hasArg = Boolean.parseBoolean(optionYaml.get("hasArg").toString());

                Option option = new Option(opt, hasArg, desc);
                if(optionYaml.containsKey("longOpt")) {
                    option.setLongOpt(optionYaml.get("longOpt").toString());
                }
                if(hasArg) {
                    if(optionYaml.containsKey("argName")) {
                        option.setArgName(optionYaml.get("argName").toString());
                    }
                    if(optionYaml.containsKey("argNumber")) {
                        option.setArgs(Integer.parseInt(optionYaml.get("argNumber").toString()));
                    }
                    if(optionYaml.containsKey("valueSeparator")) {
                        option.setValueSeparator(optionYaml.get("valueSeparator").toString().toCharArray()[0]);
                    } else {
                        option.setValueSeparator('=');
                    }
                    if(optionYaml.containsKey("type")) {
                        try {
                            option.setType(Class.forName(optionYaml.get("type").toString()));
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if(optionYaml.containsKey("required")) {
                    option.setRequired(Boolean.parseBoolean(optionYaml.get("required").toString()));
                }
                if(optionYaml.containsKey("optional")) {
                    option.setOptionalArg(Boolean.parseBoolean(optionYaml.get("optional").toString()));
                }

                options.addOption(option);
            });
            botCmd.setOptions(options);

            // 3) 解析并设置handler对象，即用于调用处理方法的对象
            String fieldName = map.get("handler").toString();
            // 反射获取handler对象
            Field field = ReflectionUtils.findField(this.getClass(), fieldName);
            try {
                botCmd.setHandler(field.get(this));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            // 4) 解析并设置handlerMethod，即命令参数组对应的处理方法
            // 这一步中的“参数”是指“方法参数列表中的参数”，请注意与上面的“命令参数”区分

            // 配置文件中方法参数列表handlerMethodArgs项为空时，统一的默认的参数列表：
            // BotCmd             匹配成功的命令BotCmd对象
            // CommandLine        解析完成的CommandLine对象
            // GroupMessageEvent  群消息事件
            // String             纯消息主体文本内容msgPlain
            List<Class<?>> defaultArgsClassList = new ArrayList<>(4);
            defaultArgsClassList.add(BotCmd.class);
            defaultArgsClassList.add(CommandLine.class);
            defaultArgsClassList.add(GroupMessageEvent.class);
            defaultArgsClassList.add(String.class);

            String methodName = map.get("handlerMethod").toString();
            Object args = map.get("handlerMethodArgs");
            // 获取方法参数列表
            List<Class<?>> argsClassList = null;
            if(args == null || StringUtils.isEmpty(args.toString()) || StringUtils.isBlank(args.toString())) {
                argsClassList = defaultArgsClassList;
            } else {
                String[] argsArray = args.toString().split(" ");
                argsClassList = new ArrayList<>(argsArray.length);
                for (String arg : argsArray) {
                    try {
                        argsClassList.add(Class.forName(arg));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
            // 反射获取方法
            Method method = ReflectionUtils.findMethod(botCmd.getHandler().getClass(), methodName, argsClassList.toArray(Class[]::new));
            botCmd.setHandlerMethod(method);

            botCmdList.add(botCmd);
        }
        return botCmdList;
    }

}
