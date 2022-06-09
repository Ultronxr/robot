package cn.ultronxr.qqrobot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Ultronxr
 * @date 2022/06/08 19:21
 *
 * 处理 web 错误代码的 controller
 */
@Slf4j
@Controller
@RequestMapping("/errorPageController")
public class ErrorPageController {

    @RequestMapping("error_{errorCode}")
    public String error(@PathVariable Integer errorCode) {
        log.warn("web 请求错误，错误代码：{}", errorCode);
        switch (errorCode) {
            case 400: return "error/400";
            case 401: return "error/401";
            case 404: return "error/404";
            case 500: return "error/500";
            default: return "error/other";
        }
    }

}
