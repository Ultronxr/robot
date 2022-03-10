package cn.ultronxr.qqrobot.controller;

import cn.ultronxr.qqrobot.bean.mybatis.bean.QuartzTask;
import cn.ultronxr.qqrobot.bean.mybatis.mapper.QuartzTaskMapper;
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
    private QuartzTaskMapper taskMapper;

    @Autowired
    private QuartzService quartzService;


    @RequestMapping("addJob")
    @ResponseBody
    public String addJob(@RequestBody QuartzTask task) {
        Date date = new Date();
        task.setCreateTime(date);
        task.setUpdateTime(date);
        if(quartzService.addJob(task)) {
            taskMapper.insert(task);
            return "OK";
        }
        return "NOT OK";
    }

    @RequestMapping("reloadJob")
    public void reloadJob() {
        quartzService.initJobs();
    }

}
