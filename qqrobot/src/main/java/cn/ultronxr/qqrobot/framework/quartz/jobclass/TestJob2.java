package cn.ultronxr.qqrobot.framework.quartz.jobclass;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Component;

/**
 * @author Ultronxr
 * @date 2022/03/05 21:08
 */
@Component
@Slf4j
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class TestJob2 implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("TestJob2执行。");
    }

}
