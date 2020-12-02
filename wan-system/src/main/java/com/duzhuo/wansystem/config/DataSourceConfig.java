package com.duzhuo.wansystem.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/12/2 13:59
 */

@Configuration
public class DataSourceConfig {

    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource getOtherDataSource(){
        return DataSourceBuilder.create().build();
    }

}