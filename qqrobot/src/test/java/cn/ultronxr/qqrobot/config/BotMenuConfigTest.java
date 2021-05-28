package cn.ultronxr.qqrobot.config;

import cn.ultronxr.qqrobot.bean.BotCmd;
import cn.ultronxr.qqrobot.bean.BotMenu;
import cn.ultronxr.qqrobot.util.CommonCliUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ReflectionUtils;

import java.util.List;

/**
 * @author Ultronxr
 * @date 2021/05/25 15:09
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
})
public class BotMenuConfigTest {

    @Autowired
    private BotMenuConfig botMenuConfig;

    @Test
    public void test(){
        List<BotCmd> botCmdList = botMenuConfig.processYaml();
        BotCmd botCmd = botCmdList.get(0);

        //String[] args = "phub --prefix=111 --suffix=222 --help".split(" ");
        String[] args = "phub 111 222".split(" ");
        CommandLine cmdLine = null;
        try {
            cmdLine = CommonCliUtils.CLI_PARSER.parse(botCmd.getOptions(), args);
        } catch (ParseException e) {
            log.warn("[BotCmd] 功能命令参数解析抛出异常：该参数组不匹配！");
            e.printStackTrace();
        }
        try {
            ReflectionUtils.invokeMethod(botCmd.getHandlerMethod(), botCmd.getHandler(), botCmd, cmdLine, null, null);
        } catch (Exception ex) {
            log.warn("[BotCmd] 方法调用抛出异常！");
            ReflectionUtils.handleReflectionException(ex);
        }
    }

    @Test
    public void test1() {
        botMenuConfig.initBotMenu();
        System.out.println(BotMenu.getMenuText());
    }

}