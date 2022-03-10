package cn.ultronxr.qqrobot.framework.quartz.jobclass;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Component;

/**
 * @author Ultronxr
 * @date 2022/03/05 21:06
 *
 * 缺省Job对象。当任务的执行Job未指定时，默认执行该Job。
 */
@Component
@Slf4j
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class DefaultJob implements Job {


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = context.getJobDetail();
        String jobName = jobDetail.getKey().getName(),
                jobGroup = context.getJobDetail().getKey().getGroup();
        log.info("[Quartz] DefaultJob({}, {}) 执行。", jobName, jobGroup);
    }

}
