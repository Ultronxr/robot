package cn.ultronxr.qqrobot.framework.quartz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author Ultronxr
 * @date 2022/03/05 14:16
 */
@Component
@Slf4j
@Order(value = 1)
public class QuartzJobInitListener implements CommandLineRunner {

    @Autowired
    QuartzService quartzService;


    @Override
    public void run(String... args) throws Exception {
        try {
            quartzService.initQuartzJobs();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
