package cn.ultronxr.qqrobot;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author Ultronxr
 * @date 2022/03/04 17:29
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class QuartzTest implements Job {

    @Test
    public void test() throws SchedulerException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.start();

        JobDetail jobDetail = newJob(QuartzTest.class)
                .withIdentity("myJob", "group1")
                .build();

        Trigger trigger = newTrigger()
                .withIdentity("myTrigger", "group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).repeatForever())
                .build();

        scheduler.scheduleJob(jobDetail, trigger);

    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("Quartz任务myJob执行");
    }
}
