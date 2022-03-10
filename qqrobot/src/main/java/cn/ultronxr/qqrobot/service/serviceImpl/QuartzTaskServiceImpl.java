package cn.ultronxr.qqrobot.service.serviceImpl;

import cn.ultronxr.qqrobot.bean.BotCmd;
import cn.ultronxr.qqrobot.service.QuartzTaskService;
import cn.ultronxr.qqrobot.util.CommonCliUtils;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.apache.commons.cli.CommandLine;
import org.springframework.stereotype.Service;

/**
 * @author Ultronxr
 * @date 2022/03/04 17:24
 */
@Service
@Slf4j
public class QuartzTaskServiceImpl implements QuartzTaskService {

    /** 提肛提醒小助手图片文件位于resources下的路径 **/
    //private static final String REMINDER_LEVATOR_ANI_MUSCLE_TRAINING_JPG_FILEPATH = "/img/quartz/reminder_LevatorAniMuscleTraining.jpg";


    @Override
    public void handleAdd(BotCmd botCmd, CommandLine cmdLine, GroupMessageEvent groupMsgEvent) {
        if(cmdLine.hasOption("help")) {
            // 优先判断 --help 选项
            CommonCliUtils.defaultOptionHelper(groupMsgEvent, botCmd);
            return;
        }
    }

    @Override
    public void handlePause(BotCmd botCmd, CommandLine cmdLine, GroupMessageEvent groupMsgEvent) {

    }

    @Override
    public void handleResume(BotCmd botCmd, CommandLine cmdLine, GroupMessageEvent groupMsgEvent) {

    }

    @Override
    public void handleStop(BotCmd botCmd, CommandLine cmdLine, GroupMessageEvent groupMsgEvent) {

    }

    @Override
    public void handleDelete(BotCmd botCmd, CommandLine cmdLine, GroupMessageEvent groupMsgEvent) {

    }

    @Override
    public void handleList(BotCmd botCmd, CommandLine cmdLine, GroupMessageEvent groupMsgEvent) {

    }

    @Override
    public void handleQuery(BotCmd botCmd, CommandLine cmdLine, GroupMessageEvent groupMsgEvent) {

    }

    @Override
    public void handleCopy(BotCmd botCmd, CommandLine cmdLine, GroupMessageEvent groupMsgEvent) {

    }

    @Override
    public void handleModify(BotCmd botCmd, CommandLine cmdLine, GroupMessageEvent groupMsgEvent) {

    }
}
