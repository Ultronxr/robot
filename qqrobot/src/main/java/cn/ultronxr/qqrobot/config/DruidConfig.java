package cn.ultronxr.qqrobot.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ultronxr
 * @date 2021/05/04 12:25
 *
 * Druid 配置
 */
@Configuration
public class DruidConfig {

    @Value("${druid.username}")
    private String DruidUsername;

    @Value("${druid.password}")
    private String DruidPassword;

    private static final String RESET_ENABLE = "false";


    /**
     * Druid Web配置
     */
    @Bean
    public ServletRegistrationBean<StatViewServlet> statViewServlet() {
        ServletRegistrationBean<StatViewServlet> servletRegistrationBean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");

        servletRegistrationBean.addInitParameter("loginUsername", DruidUsername);
        servletRegistrationBean.addInitParameter("loginPassword", DruidPassword);
        servletRegistrationBean.addInitParameter("resetEnable", RESET_ENABLE);

        // 访问白名单和黑名单
        //servletRegistrationBean.addInitParameter("allow","127.0.0.1");
        //servletRegistrationBean.addInitParameter("deny","127.0.0.1");

        return servletRegistrationBean;
    }

    /**
     * Druid Filter过滤器配置
     */
    @Bean
    public FilterRegistrationBean<WebStatFilter> statFilter(){
        FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean<>(new WebStatFilter());
        // 设置过滤器路径
        filterRegistrationBean.addUrlPatterns("/*");
        // 忽略过滤的形式
        filterRegistrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }

}
