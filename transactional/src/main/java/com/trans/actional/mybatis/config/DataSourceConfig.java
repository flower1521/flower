package com.trans.actional.mybatis.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.trans.actional.mybatis.DynamicDataSource;
import com.trans.actional.mybatis.interceptor.MybatisInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Import(MybatisInterceptor.class)
@Configuration
@PropertySource("classpath:jdbc.properties")
@MapperScan(basePackages = "com.trans.actional.mapper", sqlSessionFactoryRef = "SqlSessionFactory")
public class DataSourceConfig {
    @Autowired
    private MybatisInterceptor mybatisInterceptor;

    @Bean(name = "master")
    @Primary
    public static DataSource newDataSourceMaster(Environment environment) {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(environment.getProperty("jdbcDriver"));
        druidDataSource.setUrl(environment.getProperty("jdbcMasterUrl"));
        druidDataSource.setUsername(environment.getProperty("jdbcUsername"));
        druidDataSource.setPassword(environment.getProperty("jdbcPassword"));
        druidDataSource.setMaxActive(100);
        return druidDataSource;
    }

    @Bean(name = "slave")
    public static DataSource newDataSourceSlave(Environment environment) {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(environment.getProperty("jdbcDriver"));
        druidDataSource.setUrl(environment.getProperty("jdbcSlaveUrl"));
        druidDataSource.setUsername(environment.getProperty("jdbcUsername"));
        druidDataSource.setPassword(environment.getProperty("jdbcPassword"));
        druidDataSource.setMaxActive(100);
        return druidDataSource;
    }

    @Bean("dynamicDataSource")
    public DynamicDataSource newDynamicDataSource(@Qualifier("master") DataSource dataSourceMaster,
                                                  @Qualifier("slave") DataSource dataSourceSlave) {
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>(5);
        targetDataSources.put("master", dataSourceMaster);
        targetDataSources.put("slave", dataSourceSlave);
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setDefaultTargetDataSource(dataSourceMaster);
        dynamicDataSource.setTargetDataSources(targetDataSources);
        return dynamicDataSource;
    }

    @Bean(name = "SqlSessionFactory")
    @ConditionalOnMissingBean
    public SqlSessionFactory newSqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dynamicDataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        //自定义拦截器
        factoryBean.setPlugins(new Interceptor[]{mybatisInterceptor});
        Resource[] mapperLocations = new PathMatchingResourcePatternResolver().getResources("classpath:mapping/*Mapping.xml");
        factoryBean.setMapperLocations(mapperLocations);
        factoryBean.setDataSource(dynamicDataSource);
        return factoryBean.getObject();
    }
}
