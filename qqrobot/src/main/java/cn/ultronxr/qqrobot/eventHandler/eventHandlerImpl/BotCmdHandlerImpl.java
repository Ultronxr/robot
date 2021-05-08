package cn.ultronxr.qqrobot.eventHandler.eventHandlerImpl;

import cn.ultronxr.qqrobot.bean.BotCmd;
import cn.ultronxr.qqrobot.eventHandler.BotCmdHandler;
import cn.ultronxr.qqrobot.eventHandler.MsgSentenceHandler;
import cn.ultronxr.qqrobot.util.CommonCliUtils;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author Ultronxr
 * @date 2021/05/08 13:38
 */
@Component
@Slf4j
public class BotCmdHandlerImpl implements BotCmdHandler {

    @Autowired
    private MsgSentenceHandler msgSentenceHandler;


    @Override
    public void botCmdHandler(@NotNull BotCmd botCmd, @NotNull GroupMessageEvent groupMsgEvent, @NotNull String msgPlain) {
        String[] args = msgPlain.strip().split(" ");
        CommandLine cmdLine = null;
        List<Options> optionsList = botCmd.getOptionsList();

        for(int idx = 0; idx < optionsList.size(); ++idx) {
            try {
                cmdLine = CommonCliUtils.CLI_PARSER.parse(optionsList.get(idx), args);
            } catch (ParseException e) {
                log.warn("[BotCmd] 功能命令参数解析抛出异常：该参数组不匹配！");
                //e.printStackTrace();
                continue;
            }
            if(cmdLine != null && cmdLine.getArgs().length > 0) {
                log.info("[BotCmd] 功能命令参数解析完成，符合的参数组：{}", CommonCliUtils.describeOptions(optionsList.get(idx)));
                try {
                    botCmd.getMethodList().get(idx).invoke(msgSentenceHandler, 1);
                } catch (IllegalAccessException | InvocationTargetException ex) {
                    log.warn("[BotCmd] method.invoke() 抛出异常！");
                    ex.printStackTrace();
                }
                return;
            }
        }
    }

    @Override
    public void testHandleBotCmd(@NotNull BotCmd botCmd, @NotNull String msgPlain) {
        //String[] args = msgPlain.strip().split(" ");
        //CommandLine cmdLine = null;
        //List<Options> optionsList = botCmd.getOptionsList();
        //
        //for(int i = 0; i < optionsList.size(); ++i) {
        //    try {
        //        cmdLine = CommonCliUtils.CLI_PARSER.parse(optionsList.get(i), args);
        //    } catch (ParseException e) {
        //        log.warn("[CLI] 功能命令解析抛出异常：无法解析该参数数组！");
        //        //e.printStackTrace();
        //        continue;
        //    }
        //    if(cmdLine != null && cmdLine.getArgs().length > 0) {
        //        log.info("解析完成，接下来进行业务处理");
        //        log.info("符合的参数组：{}", CommonCliUtils.describeOptions(optionsList.get(i)));
        //        try {
        //            botCmd.getMethodList().get(i).invoke(msgSentenceHandler, 1);
        //        } catch (IllegalAccessException | InvocationTargetException e) {
        //            log.warn("[botCmd] method.invoke抛出异常！");
        //            e.printStackTrace();
        //        }
        //        return;
        //    }
        //}

    }

}
