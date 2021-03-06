package com.duzhuo.wansystem;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 启动程序
 * @EnableSwagger2 接口文档
 * @EnableScheduling 定时任务
 * @EnableCaching redis缓存
 * @author 万宏远
 */
@EnableSwagger2
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class, SecurityAutoConfiguration.class,org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
@ComponentScan(value = {"com.duzhuo.wansystem","com.duzhuo.common"})
@EnableJpaRepositories(entityManagerFactoryRef = "entityManageFactoryPrimary", transactionManagerRef = "transactionManagerPrimary",basePackages = {"com.duzhuo.wansystem.dao"})
@EntityScan(value = {"com.duzhuo.wansystem.entity"})
@EnableScheduling
@EnableCaching
public class AppRun extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(AppRun.class, args);
        System.err.println("wan启动成功！");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
        return builder.sources(AppRun.class);
    }

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}