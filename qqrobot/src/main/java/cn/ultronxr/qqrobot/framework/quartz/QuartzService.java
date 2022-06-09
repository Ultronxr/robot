package cn.ultronxr.qqrobot.framework.quartz;

import cn.ultronxr.qqrobot.bean.mybatis.bean.QuartzTask;
import cn.ultronxr.qqrobot.bean.mybatis.bean.QuartzTaskExtend;
import cn.ultronxr.qqrobot.bean.mybatis.bean.QuartzTaskKey;
import cn.ultronxr.qqrobot.bean.mybatis.bean.QuartzTaskTarget;

import java.util.List;

/**
 * @author Ultronxr
 * @date 2022/03/05 13:32
 */
public interface QuartzService {

    /**
     * 初始化所有QuartzTask任务到 scheduler，初始化流程如下：<br/>
     *
     * 从数据库表 quartz_task 中获取所有任务信息，
     * 对每一个 激活状态（{@code status=1}） 的任务调用 {@code addQuartzTask(QuartzTask)} 方法，
     * 将其加入调度器 scheduler
     */
    void initJobs();

    /**
     * 尝试向 scheduler 中添加 QuartzTask任务
     * @param task 待加入scheduler的 QuartzTask任务
     * @return 操作结果：true-成功、false-失败
     */
    Boolean addJob(QuartzTask task);

    /**
     * 尝试从 scheduler 中停止（删除） QuartzTask任务
     * @param taskKey QuartzTask任务的 Key(name, group)
     * @return 操作结果：true-成功、false-失败
     */
    Boolean stopJob(QuartzTaskKey taskKey);

    /**
     * 尝试从 scheduler 中暂停 QuartzTask任务
     * @param taskKey QuartzTask任务的 Key(name, group)
     * @return 操作结果：true-成功、false-失败
     */
    Boolean pauseJob(QuartzTaskKey taskKey);

    /**
     * 尝试从 scheduler 中恢复已暂停的 QuartzTask任务
     * @param taskKey QuartzTask任务的 Key(name, group)
     * @return 操作结果：true-成功、false-失败
     */
    Boolean resumeJob(QuartzTaskKey taskKey);


    Boolean addTask(QuartzTask task, QuartzTaskExtend taskExtend, List<QuartzTaskTarget> taskTargetList);

    Boolean deleteTask(QuartzTaskKey taskKey);

    Boolean updateTaskStatus(QuartzTaskKey taskKey, QuartzTaskStatus status);

    Boolean updateTask(QuartzTask task);

    List<QuartzTask> queryTask(QuartzTask task);

    Long countTask(QuartzTask task);

}
