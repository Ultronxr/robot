package cn.ultronxr.qqrobot.framework.quartz;

import cn.ultronxr.qqrobot.bean.mybatis.bean.QuartzJob;
import cn.ultronxr.qqrobot.bean.mybatis.bean.QuartzJobExample;
import cn.ultronxr.qqrobot.bean.mybatis.mapper.QuartzJobMapper;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Ultronxr
 * @date 2022/03/05 13:32
 */
@Service
@Slf4j
public class QuartzServiceImpl implements QuartzService {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private QuartzJobMapper quartzJobMapper;


    @Override
    public void initQuartzJobs() {
        // 查询未被删除的任务
        QuartzJobExample example = new QuartzJobExample();
        example.createCriteria().andIsDelEqualTo(0);
        List<QuartzJob> jobList = quartzJobMapper.selectByExample(example);

        // 把每个处于激活状态的任务加入调度器
        for(QuartzJob job : jobList) {
            if(QuartzJobStatus.isActivated(job.getStatus())) {
                if(addQuartzJobs(job)) {
                    log.info("[Quartz] 添加 QuartzJob({}, {}) 成功。", job.getJobName(), job.getJobGroup());
                } else {
                    log.info("[Quartz] 添加 QuartzJob({}, {}) 失败！", job.getJobName(), job.getJobGroup());
                }
            }
        }
    }

    @Override
    public Boolean addQuartzJobs(QuartzJob quartzJob) {
        // 反射获取QuartzJob任务对应的Job类
        Class<? extends Job> jobClass = null;
        try {
            jobClass = (Class<? extends Job>) (Class.forName(quartzJob.getJobClass()).getDeclaredConstructor().newInstance().getClass());
        } catch (Exception ex) {
            ex.printStackTrace();
            log.warn("[Quartz] 反射创建QuartzJob的Job类抛出异常！");
        }
        if(jobClass == null) {
            return false;
        }
        // 向 scheduler 添加任务
        try {
            if(scheduler.checkExists(new JobKey(quartzJob.getJobName(), quartzJob.getJobGroup()))) {
                log.warn("[Quartz] QuartzJob({}, {}) 已存在，无法重复添加！", quartzJob.getJobName(), quartzJob.getJobGroup());
                return false;
            }
            JobDetail jobDetail = JobBuilder
                    .newJob(jobClass)
                    .withIdentity(quartzJob.getJobName(), quartzJob.getJobGroup())
                    .withDescription(quartzJob.getJobDescription())
                    .build();
            Trigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity(quartzJob.getJobName(), quartzJob.getJobGroup())
                    .startAt(DateBuilder.futureDate(1, DateBuilder.IntervalUnit.SECOND))
                    .withSchedule(CronScheduleBuilder.cronSchedule(quartzJob.getJobCron()))
                    .build();

            // 根据QuartzJob任务的状态配置Job是否启动
            if(QuartzJobStatus.isActivated(quartzJob.getStatus())) {
                trigger.getTriggerBuilder().startNow().build();
            } else if(QuartzJobStatus.isPaused(quartzJob.getStatus())) {
                trigger.getTriggerBuilder().startAt(quartzJob.getPauseTimeLimit()).build();
            } else if(QuartzJobStatus.isStopped(quartzJob.getStatus())) {
                trigger.getTriggerBuilder().endAt(new Date()).build();
            }

            scheduler.scheduleJob(jobDetail, trigger);
            if(!scheduler.isShutdown() && !scheduler.isStarted()) {
                // 这两个if条件方法并不会返回scheduler的当前状态，而是表示曾经是否已经shutdown过了、曾经是否已经start过了。参见其JavaDoc。
                scheduler.start();
            }
        } catch (SchedulerException ex) {
            ex.printStackTrace();
            log.warn("[Quartz] 添加 QuartzJob({}, {}) 时抛出异常！", quartzJob.getJobName(), quartzJob.getJobGroup());
            return false;
        }
        return true;
    }

}
