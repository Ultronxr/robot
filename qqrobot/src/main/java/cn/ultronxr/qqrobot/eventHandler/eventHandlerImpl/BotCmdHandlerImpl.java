package cn.ultronxr.qqrobot.eventHandler.eventHandlerImpl;

import cn.ultronxr.qqrobot.bean.BotCmd;
import cn.ultronxr.qqrobot.eventHandler.BotCmdHandler;
import cn.ultronxr.qqrobot.util.CommonCliUtils;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.util.List;

/**
 * @author Ultronxr
 * @date 2021/05/08 13:38
 */
@Component
@Slf4j
public class BotCmdHandlerImpl implements BotCmdHandler {


    @Override
    public void botCmdHandler(@NotNull BotCmd botCmd, @NotNull GroupMessageEvent groupMsgEvent, @NotNull String msgPlain) {
        String[] args = msgPlain.strip().split(" +");
        List<Options> optionsList = botCmd.getOptionsList();
        CommandLine cmdLine = null;

        for(int idx = 0; idx < optionsList.size(); ++idx) {
            try {
                cmdLine = CommonCliUtils.CLI_PARSER.parse(optionsList.get(idx), args);
            } catch (ParseException e) {
                log.warn("[BotCmd] 功能命令参数解析抛出异常（index={}）：该参数组不匹配！", idx);
                //e.printStackTrace();
                continue;
            }
            if(cmdLine != null && cmdLine.getArgs().length > 0) {
                log.info("[BotCmd] 功能命令参数解析完成（index={}），符合的参数组：\n{}", idx, CommonCliUtils.describeOptions(optionsList.get(idx)));
                try {
                    // TODO 2021-5-22 23:16:47 这里不应该把传入的参数args写死，而是从handlerMethodList对应的method动态过去，不过不知道怎么实现先这样写
                    ReflectionUtils.invokeMethod(botCmd.getHandlerMethodList().get(idx), botCmd.getHandler(),
                            botCmd, idx, cmdLine, groupMsgEvent, msgPlain);
                } catch (Exception ex) {
                    log.warn("[BotCmd] 方法调用抛出异常！");
                    ReflectionUtils.handleReflectionException(ex);
                }
                return;
            }
        }
    }

}
