package cn.ultronxr.qqrobot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Ultronxr
 * @date 2022/06/08 17:50
 */
@Slf4j
@Controller
@RequestMapping("/")
public class BaseController {

    @RequestMapping({"", "index"})
    public String index() {
        log.info("请求 index 页面。");
        return "index";
    }

    @RequestMapping("quartz")
    public String quartzTask() {
        log.info("请求 quartz/list 页面。");
        return "quartz/list";
    }

}
