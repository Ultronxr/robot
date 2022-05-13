package cn.ultronxr.qqrobot.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author Ultronxr
 * @date 2022/05/13 16:25
 *
 * 数据源 配置
 * {@link "https://segmentfault.com/a/1190000040305418"}
 */
@Slf4j
@Configuration
@MapperScan(basePackages = "cn.ultronxr.qqrobot.bean.mybatis",
        sqlSessionFactoryRef = "mainSqlSessionFactory", sqlSessionTemplateRef = "mainSqlSessionTemplate")
public class MainDatasourceConfig {

    public static final String MAPPER_LOCATION = "classpath:mapper/*.xml";

    @Autowired
    private DruidConfig druidConfig;


    /** 主数据源 */
    @Primary
    @Bean("mainDataSource")
    @ConfigurationProperties("spring.datasource.druid.datasource-main")
    public DataSource dataSource(){
        // return DruidDataSourceBuilder.create().build();
        DruidDataSource druidDataSource = null;
        try {
            // 使用自定义Druid配置内容创建数据源
            druidDataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(druidConfig.getProperties());
            // 失败后重连的次数
            druidDataSource.setConnectionErrorRetryAttempts(3);
            //请求失败之后中断
            druidDataSource.setBreakAfterAcquireFailure(true);
        } catch (Exception ex) {
            log.error("[Configuration] 创建自定义配置的druid数据源时抛出异常！");
            ex.printStackTrace();
        }
        return druidDataSource;
    }

    @Primary
    @Bean("mainTransactionManager")
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Primary
    @Bean("mainSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("mainDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));
        return sqlSessionFactoryBean.getObject();
    }

    @Primary
    @Bean("mainSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("mainDataSource") DataSource dataSource) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory(dataSource));
    }

}
