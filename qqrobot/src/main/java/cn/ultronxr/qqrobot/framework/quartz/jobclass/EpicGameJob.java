package cn.ultronxr.qqrobot.framework.quartz.jobclass;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Component;

/**
 * @author Ultronxr
 * @date 2022/05/14 18:14
 *
 * 定时获取Epic游戏商店免费游戏信息 Job
 */
@Component
@Slf4j
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class EpicGameJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

    }
}
