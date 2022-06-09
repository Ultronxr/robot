package cn.ultronxr.qqrobot.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * @author Ultronxr
 * @date 2022/06/08 19:19
 *
 * web 错误代码配置，转交给自定义的controller处理
 */
@Component
public class ErrorPageConfig implements ErrorPageRegistrar {

    private static final String CONTROLLER_PREFIX = "/errorPageController/error_";


    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        ErrorPage error400Page = new ErrorPage(HttpStatus.BAD_REQUEST, CONTROLLER_PREFIX + "400");
        ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, CONTROLLER_PREFIX + "401");
        ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, CONTROLLER_PREFIX + "404");
        ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, CONTROLLER_PREFIX + "500");
        registry.addErrorPages(error400Page, error401Page, error404Page, error500Page);
    }

}
