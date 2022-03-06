package cn.ultronxr.qqrobot.framework.quartz;

import cn.ultronxr.qqrobot.bean.mybatis.bean.QuartzJob;

/**
 * @author Ultronxr
 * @date 2022/03/05 13:32
 */
public interface QuartzService {

    /**
     * 初始化所有QuartzJob任务，初始化流程如下：<br/>
     *
     * 从数据库表 quartz_job 中获取所有未被删除（{@code is_del=0}）的任务信息，
     * 对每一个 激活状态（{@code status=1}） 的任务调用 {@code addQuartzJobs(QuartzJob)} 方法，
     * 将其加入调度器 scheduler
     */
    void initQuartzJobs();

    /**
     * 尝试把QuartzJob任务加入 scheduler
     *
     * @param quartzJob 待加入scheduler的 QuartzJob任务
     * @return 任务加入调度器是否成功：true-成功、false-失败
     */
    Boolean addQuartzJobs(QuartzJob quartzJob);

}
