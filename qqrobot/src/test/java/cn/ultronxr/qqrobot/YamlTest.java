package cn.ultronxr.qqrobot;

import cn.ultronxr.qqrobot.bean.BotCmd;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.yaml.snakeyaml.Yaml;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Ultronxr
 * @date 2021/05/11 15:38
 */
public class YamlTest {

    //@SuppressWarnings("unchecked")
    public static void main(String[] args) {
        Yaml yaml = new Yaml();
        Iterable<Object> yamlAll = yaml.loadAll(YamlTest.class.getResourceAsStream("/botMenu.yaml"));

        List<BotCmd> botMenu = new ArrayList<>();

        while (yamlAll.iterator().hasNext()) {
            LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) yamlAll.iterator().next();
            System.out.println(map);

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

            Class<?> handlerClass = null;
            try {
                handlerClass = Class.forName(map.get("handler").toString());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            botCmd.setHandler(handlerClass);
            try {
                botCmd.setHandler(handlerClass.getDeclaredConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }

            List<Method> methodList = new ArrayList<>(optionsList.size());
            List<LinkedHashMap<String, Object>> methodListYaml = (List<LinkedHashMap<String, Object>>) map.get("handlerMethodList");
            Class<?> finalHandlerClass = handlerClass;
            methodListYaml.forEach(methodYaml -> {
                String methodName = methodYaml.get("method").toString();
                String[] argsArray = methodYaml.get("args").toString().split(" ");
                List<Class<?>> classList = new ArrayList<>(argsArray.length);
                for (String arg : argsArray) {
                    try {
                        classList.add(Class.forName(arg));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Method method = finalHandlerClass.getDeclaredMethod(methodName, classList.toArray(Class[]::new));
                    methodList.add(method);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            });
            botCmd.setHandlerMethodList(methodList);

            botMenu.add(botCmd);
        }

        BotCmd botCmd = botMenu.get(0);
        try {
            botCmd.getHandlerMethodList().get(0).invoke(botCmd.getHandler(), null, "kjhjk", null);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        System.out.println();

    }

}
