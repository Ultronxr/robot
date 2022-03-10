package cn.ultronxr.qqrobot.framework.quartz;

import cn.hutool.core.date.DateTime;
import cn.ultronxr.qqrobot.bean.mybatis.bean.*;
import cn.ultronxr.qqrobot.bean.mybatis.mapper.QuartzTaskExtendMapper;
import cn.ultronxr.qqrobot.bean.mybatis.mapper.QuartzTaskMapper;
import cn.ultronxr.qqrobot.bean.mybatis.mapper.QuartzTaskTargetMapper;
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
    private QuartzTaskMapper taskMapper;

    @Autowired
    private QuartzTaskExtendMapper taskExtendMapper;

    @Autowired
    private QuartzTaskTargetMapper taskTargetMapper;


    @Override
    public void initJobs() {
        // 获取所有任务并把每个任务加入调度器
        List<QuartzTask> taskList = taskMapper.selectByExample(null);
        for(QuartzTask task : taskList) {
            if(addJob(task)) {
                log.info("[Quartz] 添加 QuartzJob({}, {}) 成功。", task.getTaskName(), task.getTaskGroup());
            } else {
                log.info("[Quartz] 添加 QuartzJob({}, {}) 失败！", task.getTaskName(), task.getTaskGroup());
            }
        }
    }

    @Override
    public Boolean addJob(QuartzTask task) {
        // 反射获取QuartzJob任务对应的Job类
        Class<? extends Job> jobClass = null;
        try {
            jobClass = (Class<? extends Job>) (Class.forName(task.getTaskClass()).getDeclaredConstructor().newInstance().getClass());
        } catch (Exception ex) {
            ex.printStackTrace();
            log.warn("[Quartz] 反射创建QuartzJob的Job类抛出异常！");
        }
        if(jobClass == null) {
            return false;
        }
        // 向 scheduler 添加任务
        try {
            if(scheduler.checkExists(new JobKey(task.getTaskName(), task.getTaskGroup()))) {
                log.warn("[Quartz] QuartzJob({}, {}) 已存在，无法重复添加！", task.getTaskName(), task.getTaskGroup());
                return false;
            }
            JobDetail jobDetail = JobBuilder
                    .newJob(jobClass)
                    .withIdentity(task.getTaskName(), task.getTaskGroup())
                    .withDescription(task.getTaskDescription())
                    .build();
            Trigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity(task.getTaskName(), task.getTaskGroup())
                    .startAt(DateBuilder.futureDate(1, DateBuilder.IntervalUnit.SECOND))
                    .withSchedule(CronScheduleBuilder.cronSchedule(task.getTaskCron()))
                    .build();

            // 根据QuartzJob任务的状态配置Job是否启动
            if(QuartzTaskStatus.isActivated(task.getStatus())) {
                trigger.getTriggerBuilder().startNow().build();
            } else if(QuartzTaskStatus.isPaused(task.getStatus())) {
                trigger.getTriggerBuilder().startAt(task.getPauseTimeLimit()).build();
            } else if(QuartzTaskStatus.isStopped(task.getStatus())) {
                trigger.getTriggerBuilder().endAt(new Date()).build();
            }

            scheduler.scheduleJob(jobDetail, trigger);
            if(!scheduler.isShutdown() && !scheduler.isStarted()) {
                // 这两个if条件方法并不会返回scheduler的当前状态，而是表示曾经是否已经shutdown过了、曾经是否已经start过了。参见其JavaDoc。
                scheduler.start();
            }
        } catch (SchedulerException ex) {
            ex.printStackTrace();
            log.warn("[Quartz] 添加 QuartzJob({}, {}) 时抛出异常！", task.getTaskName(), task.getTaskGroup());
            return false;
        }
        return true;
    }

    @Override
    public Boolean stopJob(QuartzTaskKey taskKey) {
        try {
            scheduler.deleteJob(new JobKey(taskKey.getTaskName(), taskKey.getTaskGroup()));
        } catch (SchedulerException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Boolean pauseJob(QuartzTaskKey taskKey) {
        try {
            scheduler.pauseJob(new JobKey(taskKey.getTaskName(), taskKey.getTaskGroup()));
        } catch (SchedulerException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Boolean resumeJob(QuartzTaskKey taskKey) {
        try {
            scheduler.resumeJob(new JobKey(taskKey.getTaskName(), taskKey.getTaskGroup()));
        } catch (SchedulerException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Boolean addTask(QuartzTask task, QuartzTaskExtend taskExtend, List<QuartzTaskTarget> taskTargetList) {
        if(null != task) {
            DateTime date = DateTime.now();
            if(null == task.getCreateTime()) {
                task.setCreateTime(date);
            }
            if(null == task.getUpdateTime()) {
                task.setUpdateTime(date);
            }
            if(null == taskMapper.selectByPrimaryKey(new QuartzTaskKey(task.getTaskName(), task.getTaskGroup()))) {
                taskMapper.insert(task);
                if(null != taskExtend) {
                    taskExtendMapper.insert(taskExtend);
                }
            }
        }
        if(null != taskTargetList && taskTargetList.size() > 0) {
            taskTargetList.forEach(taskTarget -> {
                taskTargetMapper.insert(taskTarget);
            });
        }
        return true;
    }

    @Override
    public Boolean deleteTask(QuartzTaskKey taskKey) {
        taskMapper.deleteByPrimaryKey(taskKey);
        taskExtendMapper.deleteByPrimaryKey(new QuartzTaskExtendKey(taskKey.getTaskName(), taskKey.getTaskGroup()));
        QuartzTaskTargetExample example = new QuartzTaskTargetExample();
        example.createCriteria()
                .andTaskNameEqualTo(taskKey.getTaskName())
                .andTaskGroupEqualTo(taskKey.getTaskGroup());
        taskTargetMapper.deleteByExample(example);
        return true;
    }

    @Override
    public Boolean updateTaskStatus(QuartzTaskKey taskKey, QuartzTaskStatus status) {
        QuartzTask task = taskMapper.selectByPrimaryKey(taskKey);
        task.setStatus(status.getStatus());
        updateTask(task);
        return true;
    }

    @Override
    public Boolean updateTask(QuartzTask task) {
        taskMapper.updateByPrimaryKeySelective(task);
        task.setUpdateTime(DateTime.now());
        return true;
    }

    @Override
    public List<QuartzTask> queryTask(QuartzTaskStatus status) {
        QuartzTaskExample example = null;
        if(null != status) {
            example = new QuartzTaskExample();
            example.createCriteria()
                    .andStatusEqualTo(status.getStatus());
        }
        return taskMapper.selectByExample(example);
    }

}
