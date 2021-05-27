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
        Options options = botCmd.getOptions();
        CommandLine cmdLine = null;

        try {
            cmdLine = CommonCliUtils.CLI_PARSER.parse(options, args);
        } catch (ParseException e) {
            log.warn("[BotCmd] 功能命令参数解析抛出异常：参数组不匹配！");
            CommonCliUtils.defaultOptionExceptionHandler(groupMsgEvent);
        }
        log.info("[BotCmd] 功能命令参数解析完成，符合的命令：\n{}", botCmd.getBriefDescription());
        if(cmdLine != null && cmdLine.getArgs().length > 0) {
            try {
                // TODO 2021-5-22 23:16:47 这里不应该把传入的参数args写死，而是从handlerMethod对应的方法参数动态传过去，不过不知道怎么实现先这样写
                ReflectionUtils.invokeMethod(botCmd.getHandlerMethod(), botCmd.getHandler(), botCmd, cmdLine, groupMsgEvent, msgPlain);
            } catch (Exception ex) {
                log.warn("[BotCmd] 方法调用invokeMethod抛出异常！");
                ReflectionUtils.handleReflectionException(ex);
            }
        }
    }

}
