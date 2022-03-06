package cn.ultronxr.qqrobot.controller;

import cn.ultronxr.qqrobot.bean.mybatis.bean.QuartzJob;
import cn.ultronxr.qqrobot.bean.mybatis.mapper.QuartzJobMapper;
import cn.ultronxr.qqrobot.framework.quartz.QuartzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * @author Ultronxr
 * @date 2022/03/05 13:35
 */
@Controller
public class TestController {

    @Autowired
    private QuartzJobMapper quartzJobMapper;

    @Autowired
    private QuartzService quartzService;


    @RequestMapping("addJob")
    @ResponseBody
    public String addJob(@RequestBody QuartzJob quartzJob) {
        Date date = new Date();
        quartzJob.setCreateTime(date);
        quartzJob.setUpdateTime(date);
        if(quartzService.addQuartzJobs(quartzJob)) {
            quartzJobMapper.insert(quartzJob);
            return "OK";
        }
        return "NOT OK";
    }

    @RequestMapping("reloadJob")
    public void reloadJob() {
        quartzService.initQuartzJobs();
    }

}
