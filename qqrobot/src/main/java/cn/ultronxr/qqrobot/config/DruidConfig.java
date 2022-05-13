package cn.ultronxr.qqrobot.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author Ultronxr
 * @date 2021/05/04 12:25
 *
 * Druid 配置
 */
@Configuration
public class DruidConfig {

    @Value("${spring.datasource.type}")
    private String type;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.druid.filters}")
    private String filters;

    @Value("${spring.datasource.druid.initialSize}")
    private String initialSize;

    @Value("${spring.datasource.druid.maxActive}")
    private String maxActive;

    @Value("${spring.datasource.druid.maxWait}")
    private String maxWait;

    @Value("${spring.datasource.druid.minIdle}")
    private String minIdle;

    @Value("${spring.datasource.druid.timeBetweenEvictionRunsMillis}")
    private String timeBetweenEvictionRunsMillis;

    @Value("${spring.datasource.druid.minEvictableIdleTimeMillis}")
    private String minEvictableIdleTimeMillis;

    @Value("${spring.datasource.druid.validationQuery}")
    private String validationQuery;

    @Value("${spring.datasource.druid.testWhileIdle}")
    private String testWhileIdle;

    @Value("${spring.datasource.druid.testOnBorrow}")
    private String testOnBorrow;

    @Value("${spring.datasource.druid.testOnReturn}")
    private String testOnReturn;

    @Value("${spring.datasource.druid.poolPreparedStatements}")
    private String poolPreparedStatements;

    @Value("${spring.datasource.druid.maxOpenPreparedStatements}")
    private String maxOpenPreparedStatements;

    /** 注意这个 username 是访问druid web控制台页面的用户名，不是连接数据库的用户名 */
    @Value("${spring.datasource.druid.username}")
    private String username;

    /** 注意这个 password 是访问druid web控制台页面的密码，不是连接数据库的密码 */
    @Value("${spring.datasource.druid.password}")
    private String password;

    private static final String RESET_ENABLE = "false";


    /**
     * 读取自定义Druid配置内容，提供给数据源创建<br/>
     * {@code DruidDataSourceFactory.createDataSource(properties)}
     */
    public Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty("type", type);
        properties.setProperty("driver-class-name", driverClassName);
        properties.setProperty("filters", filters);
        properties.setProperty("initialSize", initialSize);
        properties.setProperty("maxActive", maxActive);
        properties.setProperty("maxWait", maxWait);
        properties.setProperty("minIdle", minIdle);
        properties.setProperty("timeBetweenEvictionRunsMillis", timeBetweenEvictionRunsMillis);
        properties.setProperty("minEvictableIdleTimeMillis", minEvictableIdleTimeMillis);
        properties.setProperty("validationQuery", validationQuery);
        properties.setProperty("testWhileIdle", testWhileIdle);
        properties.setProperty("testOnBorrow", testOnBorrow);
        properties.setProperty("testOnReturn", testOnReturn);
        properties.setProperty("poolPreparedStatements", poolPreparedStatements);
        properties.setProperty("maxOpenPreparedStatements", maxOpenPreparedStatements);
        return properties;
    }

    /**
     * Druid Web配置
     */
    @Bean
    public ServletRegistrationBean<StatViewServlet> statViewServlet() {
        ServletRegistrationBean<StatViewServlet> servletRegistrationBean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");

        servletRegistrationBean.addInitParameter("loginUsername", username);
        servletRegistrationBean.addInitParameter("loginPassword", password);
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
