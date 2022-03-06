package cn.ultronxr.qqrobot.framework.quartz.jobclass;

import cn.ultronxr.qqrobot.bean.mybatis.mapper.QuartzJobMapper;
import cn.ultronxr.qqrobot.bean.mybatis.mapper.QuartzJobTargetMapper;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Ultronxr
 * @date 2022/03/05 21:06
 */
@Component
@Slf4j
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class TestJob implements Job {

    @Autowired
    private QuartzJobMapper quartzJobMapper;

    @Autowired
    private QuartzJobTargetMapper quartzJobTargetMapper;


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = context.getJobDetail();
        String jobName = jobDetail.getKey().getName(),
                jobGroup = context.getJobDetail().getKey().getGroup();
        log.info("TestJob({}, {}) 执行。", jobName, jobGroup);
    }

}
