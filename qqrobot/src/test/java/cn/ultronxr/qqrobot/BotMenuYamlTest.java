package cn.ultronxr.qqrobot;

import cn.ultronxr.qqrobot.bean.BotCmd;
import cn.ultronxr.qqrobot.eventHandler.MsgRobotMenuHandler;
import cn.ultronxr.qqrobot.eventHandler.MsgWeatherHandler;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ReflectionUtils;
import org.yaml.snakeyaml.Yaml;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Ultronxr
 * @date 2021/05/11 15:38
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        cn.ultronxr.qqrobot.eventHandler.MsgWeatherHandler.class,
        cn.ultronxr.qqrobot.eventHandler.eventHandlerImpl.MsgWeatherHandlerImpl.class,
        cn.ultronxr.qqrobot.service.WeatherService.class,
        cn.ultronxr.qqrobot.service.serviceImpl.WeatherServiceImpl.class,
        cn.ultronxr.qqrobot.eventHandler.MsgRobotMenuHandler.class,
        cn.ultronxr.qqrobot.eventHandler.eventHandlerImpl.MsgRobotMenuHandlerImpl.class,
})
public class BotMenuYamlTest {

    @Autowired
    private MsgWeatherHandler msgWeatherHandler;

    @Autowired
    private MsgRobotMenuHandler msgRobotMenuHandler;

    @Test
    public void test1() {
        //String fieldName = "msgWeatherHandler";
        //Field field = ReflectionUtils.findField(this.getClass(), fieldName);
        //Method method = ReflectionUtils.findMethod(MsgWeatherHandler.class, "groupWeatherHandler", GroupMessageEvent.class, String.class, Options.class);
        //try {
        //    ReflectionUtils.invokeMethod(method, field.get(this), null, "1", null);
        //} catch (IllegalAccessException e) {
        //    e.printStackTrace();
        //}

        List<BotCmd> botCmdList = processYaml();
        BotCmd botCmd = botCmdList.get(0);
        ReflectionUtils.invokeMethod(botCmd.getHandlerMethodList().get(0), botCmd.getHandler(), null, "1", null);

        log.info("");
    }

    @SuppressWarnings("unchecked")
    private List<BotCmd> processYaml() {
        Yaml yaml = new Yaml();
        Iterable<Object> yamlAll = yaml.loadAll(BotMenuYamlTest.class.getResourceAsStream("/botMenu.yaml"));

        List<BotCmd> botCmdList = new ArrayList<>();

        while (yamlAll.iterator().hasNext()) {
            LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) yamlAll.iterator().next();
            //System.out.println(map);

            BotCmd botCmd = new BotCmd();
            botCmd.setBriefDesc(map.get("briefDesc").toString());
            botCmd.setDetailedDesc(map.get("detailedDesc").toString());
            botCmd.setTriggerList((List<String>) map.get("triggerList"));

            List<List<LinkedHashMap<String, Object>>> optionsListYaml = (List<List<LinkedHashMap<String, Object>>>) map.get("optionsList");
            List<Options> optionsList = new ArrayList<>(optionsListYaml.size());
            optionsListYaml.forEach(optionsYaml -> {
                if(optionsYaml == null) {
                    optionsList.add(new Options());
                } else {
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

            String fieldName = map.get("handler").toString();
            Field field = ReflectionUtils.findField(this.getClass(), fieldName);
            try {
                botCmd.setHandler(field.get(this));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            List<Method> methodList = new ArrayList<>(optionsList.size());
            List<LinkedHashMap<String, Object>> methodListYaml = (List<LinkedHashMap<String, Object>>) map.get("handlerMethodList");
            List<Class<?>> defaultArgsClassList = new ArrayList<>(3);
            defaultArgsClassList.add(GroupMessageEvent.class);
            defaultArgsClassList.add(String.class);
            defaultArgsClassList.add(Options.class);

            methodListYaml.forEach(methodYaml -> {
                String methodName = methodYaml.get("method").toString();
                Object args = methodYaml.get("args");
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
                Method method = ReflectionUtils.findMethod(botCmd.getHandler().getClass(), methodName, argsClassList.toArray(Class[]::new));
                methodList.add(method);
            });
            botCmd.setHandlerMethodList(methodList);

            botCmdList.add(botCmd);
        }
        return botCmdList;
    }

}
