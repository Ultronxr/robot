package cn.ultronxr.qqrobot.framework.quartz.jobclass;

import cn.ultronxr.qqrobot.bean.BotEntity;
import cn.ultronxr.qqrobot.bean.mybatis.bean.*;
import cn.ultronxr.qqrobot.bean.mybatis.mapper.QuartzTaskExtendMapper;
import cn.ultronxr.qqrobot.bean.mybatis.mapper.QuartzTaskMapper;
import cn.ultronxr.qqrobot.bean.mybatis.mapper.QuartzTaskTargetMapper;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import org.jetbrains.annotations.NotNull;
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
public class QQGroupQuartzTaskJob implements Job {

    @Autowired
    private QuartzTaskMapper taskMapper;

    @Autowired
    private QuartzTaskExtendMapper taskExtendMapper;

    @Autowired
    private QuartzTaskTargetMapper taskTargetMapper;


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        final QuartzTaskKey taskKey = new QuartzTaskKey(
                context.getJobDetail().getKey().getName(),
                context.getJobDetail().getKey().getGroup()
        );
        // 用Key(name, group)查出QuartzTask
        final QuartzTask task = getQuartzTaskByKey(taskKey);
        // 用Key查出此任务的目标群/QQ
        final List<QuartzTaskTarget> taskTargetList = getQuartzTaskTargetListByKey(taskKey);
        // 过滤任务目标类别之后执行任务
        filterTaskTarget(taskKey, task, taskTargetList);
    }

    /**
     * 根据 Key(name, group) 查出对应的任务 QuartzTask
     * @param taskKey QuartzTaskKey对象，即 quartz_task 表的主键
     * @return quartz_task 表中的记录对应的 QuartzTask 对象
     */
    public final QuartzTask getQuartzTaskByKey(final QuartzTaskKey taskKey) {
        return taskMapper.selectByPrimaryKey(taskKey);
    }

    /**
     * 根据 Key(name, group) 查出对应的任务目标 QuartzTaskTarget 列表
     *  @param taskKey QuartzTaskKey对象
     * @return {@code List<QuartzTaskTarget>} 任务目标列表
     */
    public final List<QuartzTaskTarget> getQuartzTaskTargetListByKey(final @NotNull QuartzTaskKey taskKey) {
        QuartzTaskTargetExample taskTargetExample = new QuartzTaskTargetExample();
        taskTargetExample.createCriteria()
                .andTaskNameEqualTo(taskKey.getTaskName())
                .andTaskGroupEqualTo(taskKey.getTaskGroup());
        return taskTargetMapper.selectByExample(taskTargetExample);
    }

    /**
     * 过滤任务目标类别
     * @param taskKey        QuartzTaskKey QuartzTask对象的Key
     * @param task           QuartzTask 任务对象
     * @param taskTargetList QuartzTaskTarget 任务目标列表
     */
    public void filterTaskTarget(final QuartzTaskKey taskKey, final QuartzTask task, final List<QuartzTaskTarget> taskTargetList) {
        if(taskTargetList != null && taskTargetList.size() > 0) {
            QuartzTaskExtend taskExtend = taskExtendMapper.selectByPrimaryKey(new QuartzTaskExtendKey(taskKey.getTaskName(), taskKey.getTaskGroup()));
            taskTargetList.forEach(taskTarget -> {
                if(taskTarget.getGroupId() != null) {
                    if(taskTarget.getQqId() != null) {
                        doTaskOfGroupMember(taskExtend, taskTarget);
                    } else {
                        doTaskOfGroup(taskExtend, taskTarget);
                    }
                } else {
                    if(taskTarget.getQqId() != null) {
                        doTaskOfQQ(taskExtend, taskTarget);
                    } else {
                        doNothing();
                    }
                }
            });
        }
    }

    /**
     * 执行任务 - 群
     */
    public void doTaskOfGroup(QuartzTaskExtend taskExtend, QuartzTaskTarget taskTarget) {
        Group group = BotEntity.BOT_ENTITY.getGroup(Long.parseLong(taskTarget.getGroupId()));

    }

    /**
     * 执行任务 - 群成员
     */
    public void doTaskOfGroupMember(QuartzTaskExtend taskExtend, QuartzTaskTarget taskTarget) {

    }

    /**
     * 执行任务 - QQ
     */
    public void doTaskOfQQ(QuartzTaskExtend taskExtend, QuartzTaskTarget taskTarget) {

    }

    /**
     * 不做任何事
     */
    public void doNothing() {}

    public MessageChain buildMsgChain(QuartzTaskExtend taskExtend) {
        MessageChainBuilder msgChainBuilder = new MessageChainBuilder();
        if(taskExtend.getMsg() != null) {
            msgChainBuilder.add(taskExtend.getMsg());
        }
        if(taskExtend.getFiles() != null) {
            String[] filepathList = taskExtend.getFiles().split(",");
            for(String filepath : filepathList) {

                //File file = new File(filepath);
                //Group group = new Group
                //msgChainBuilder.add;
            }
        }
        if(taskExtend.getSms() != null) {
            // SMS
        }
        if(taskExtend.getEmail() != null) {
            // Email
        }
        return msgChainBuilder.build();
    }

}
