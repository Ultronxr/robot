package cn.ultronxr.qqrobot.framework.quartz.jobclass;

import cn.ultronxr.qqrobot.bean.mybatis.bean.QuartzJob;
import cn.ultronxr.qqrobot.bean.mybatis.bean.QuartzJobExample;
import cn.ultronxr.qqrobot.bean.mybatis.mapper.QuartzJobMapper;
import cn.ultronxr.qqrobot.bean.mybatis.mapper.QuartzJobTargetMapper;
import cn.ultronxr.qqrobot.framework.quartz.QuartzJobStatus;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Ultronxr
 * @date 2022/03/06 17:59
 */
@Component
@Slf4j
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class QQGroupReminderJob implements Job {

    @Autowired
    private QuartzJobMapper quartzJobMapper;

    @Autowired
    private QuartzJobTargetMapper quartzJobTargetMapper;


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = context.getJobDetail();
        String jobName = jobDetail.getKey().getName(),
                jobGroup = jobDetail.getKey().getGroup();
        QuartzJobExample example = new QuartzJobExample();
        example.createCriteria()
                .andIsDelEqualTo(QuartzJobStatus.DeleteStatus.NOT_DELETED.getStatus())
                .andJobNameEqualTo(jobName)
                .andJobGroupEqualTo(jobGroup);
        List<QuartzJob> jobList = quartzJobMapper.selectByExample(example);
        if(jobList != null && jobList.size() > 0) {
            QuartzJob job = jobList.get(0);

        }
    }

}
