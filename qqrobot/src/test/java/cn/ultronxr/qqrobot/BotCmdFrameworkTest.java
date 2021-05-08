package cn.ultronxr.qqrobot;

import cn.ultronxr.qqrobot.bean.BotCmd;
import cn.ultronxr.qqrobot.bean.BotMenu;
import cn.ultronxr.qqrobot.eventHandler.BotCmdHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Ultronxr
 * @date 2021/05/08 14:04
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class BotCmdFrameworkTest {

    @Autowired
    private BotMenu botMenu;

    @Autowired
    private BotCmdHandler botCmdHandler;

    @Test
    public void test() {
        //String msgPlain = "> 芬芳";
        //msgPlain = msgPlain.replaceFirst(">", "").strip();
        //BotCmd botCmd = botMenu.checkBotCmd(msgPlain);
        //if(botCmd != null) {
        //    botCmdHandler.testHandleBotCmd(botCmd, msgPlain);
        //}


    }

}
