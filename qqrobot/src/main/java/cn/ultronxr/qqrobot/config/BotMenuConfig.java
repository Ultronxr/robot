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
 * TODO 2021-5-20 22:48:54 把eventHandler包里的handler的逻辑处理代码抽取到service包里去，只保留最基本的与groupMessageEvent交互的代码
 * TODO 2021-5-20 22:39:59 需要在README里面详细写一份添加一条BotCmd需要哪些步骤的说明
 * 添加一条BotCmd需要的步骤说明：
 * 例如：1. eventHandler包添加事件handler方法（service对应的逻辑处理方法）
 *      2. 在botMenu.yaml配置好详细内容
 *      3.最后在这里Autowired这个handler方法
 */
@Configuration
@Slf4j
public class BotMenuConfig {

    /** botCmd/botMenu配置文件路径，以 resources包 为根目录 */
    public static final String CONFIG_YAML_PATH = "/botMenu.yaml";

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


    /**
     * 初始化BOT命令菜单BotMenu
     * 其他地方调用命令菜单也直接用static方法即可
     */
    @Bean
    public void initBotMenu() {
        List<BotCmd> botCmdList = processYaml();
        BotMenu.botCmdList = (ArrayList<BotCmd>) botCmdList;
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
    private List<BotCmd> processYaml() {
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

            // 2) 解析并设置功能命令参数组列表optionsList
            List<List<LinkedHashMap<String, Object>>> optionsListYaml = (List<List<LinkedHashMap<String, Object>>>) map.get("optionsList");
            List<Options> optionsList = new ArrayList<>(optionsListYaml.size());
            // 遍历功能命令参数组列表optionsList
            optionsListYaml.forEach(optionsYaml -> {
                if(optionsYaml == null) {
                    // 无命令参数，添加一个空参数组
                    optionsList.add(new Options());
                } else {
                    // 有命令参数，解析并设置参数组Options
                    // 遍历每一项命令参数Option（参数内的opt、desc、hasArg等含义请另参见Option的用法），加入参数组Options
                    Options options = new Options();
                    optionsYaml.forEach(optionYaml -> {
                        String opt = optionYaml.get("opt").toString();
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
                        options.addOption(option);
                    });
                    optionsList.add(options);
                }
            });
            botCmd.setOptionsList(optionsList);

            // 3) 解析并设置handler对象，即用于调用处理方法的对象
            String fieldName = map.get("handler").toString();
            // 反射获取handler对象
            Field field = ReflectionUtils.findField(this.getClass(), fieldName);
            try {
                botCmd.setHandler(field.get(this));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            // 4) 解析并设置handlerMethodList，即每一个参数组对应的处理方法
            // 这一步中的参数是指“方法参数列表中的参数”，请注意与上面的“命令参数”区分
            List<Method> methodList = new ArrayList<>(optionsList.size());
            List<LinkedHashMap<String, Object>> methodListYaml = (List<LinkedHashMap<String, Object>>) map.get("handlerMethodList");

            // 配置文件中方法参数列表args项为空时，统一的默认的参数列表：
            // BotCmd             匹配成功的命令BotCmd对象
            // Integer            Options参数组/对应处理方法Method的列表下标idx
            // CommandLine        解析完成的CommandLine对象
            // GroupMessageEvent  群消息事件
            // String             纯消息主体文本内容msgPlain
            List<Class<?>> defaultArgsClassList = new ArrayList<>(3);
            defaultArgsClassList.add(BotCmd.class);
            defaultArgsClassList.add(Integer.class);
            defaultArgsClassList.add(CommandLine.class);
            defaultArgsClassList.add(GroupMessageEvent.class);
            defaultArgsClassList.add(String.class);

            // 遍历处理方法列表
            methodListYaml.forEach(methodYaml -> {
                String methodName = methodYaml.get("method").toString();
                Object args = methodYaml.get("args");
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
                methodList.add(method);
            });
            botCmd.setHandlerMethodList(methodList);

            botCmdList.add(botCmd);
        }
        return botCmdList;
    }

}
