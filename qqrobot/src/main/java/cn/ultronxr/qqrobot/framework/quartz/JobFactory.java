package cn.ultronxr.qqrobot.framework.quartz;

import org.jetbrains.annotations.NotNull;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

/**
 * @author Ultronxr
 * @date 2022/03/05 21:03
 */
@Component
public class JobFactory extends AdaptableJobFactory {

    @Autowired
    private AutowireCapableBeanFactory autowireCapableBeanFactory;

    @Override
    protected @NotNull Object createJobInstance(@NotNull TriggerFiredBundle triggerFiredBundle) throws Exception {
        Object jobInstance = super.createJobInstance(triggerFiredBundle);
        autowireCapableBeanFactory.autowireBean(jobInstance);
        return jobInstance;
    }

}
