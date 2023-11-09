package com.shiwei.pay.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import io.github.greenhandsw.split.DatabaseContextHolder;
import io.github.greenhandsw.split.DynamicDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {
    @Bean(name = "datasource")
    @Primary
    public DataSource dataSource(){
        DynamicDataSource proxy=new DynamicDataSource();
        Map<Object, Object> targetDataSources=new HashMap<>();
        targetDataSources.put(DatabaseContextHolder.DbType.MASTER, datasourceMaster());
        targetDataSources.put(DatabaseContextHolder.DbType.SLAVE1, datasourceSlave1());
        targetDataSources.put(DatabaseContextHolder.DbType.SLAVE2, datasourceSlave2());
        proxy.setDefaultTargetDataSource(datasourceMaster());
        proxy.setTargetDataSources(targetDataSources);
        proxy.afterPropertiesSet();
        return proxy;
    }

    @Bean(name = "datasourceMaster")
    @ConfigurationProperties(prefix = "druid.pay.master")
    public DataSource datasourceMaster(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "datasourceSlave1")
    @ConfigurationProperties(prefix = "druid.pay.slave1")
    public DataSource datasourceSlave1(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "datasourceSlave2")
    @ConfigurationProperties(prefix = "druid.pay.slave2")
    public DataSource datasourceSlave2(){
        return DruidDataSourceBuilder.create().build();
    }
}
