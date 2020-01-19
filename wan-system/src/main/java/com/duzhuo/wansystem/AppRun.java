package com.duzhuo.wansystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 启动程序
 *
 * @author wanhy
 */
@EnableSwagger2
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@ComponentScan(value = {"com.duzhuo.admin","com.duzhuo.wansystem","com.duzhuo.common"})
@EnableJpaRepositories(value = {"com.duzhuo.wansystem.dao"})
@EntityScan(value = {"com.duzhuo.wansystem.entity"})
@EnableScheduling
public class AppRun extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(AppRun.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  wan启动成功！   ლ(´ڡ`ლ)ﾞ  \n" +
                " .-------.       ____     __        \n" +
                " |  _ _   \\      \\   \\   /  /    \n" +
                " | ( ' )  |       \\  _. /  '       \n" +
                " |(_ o _) /        _( )_ .'         \n" +
                " | (_,_).' __  ___(_ o _)'          \n" +
                " |  |\\ \\  |  ||   |(_,_)'         \n" +
                " |  | \\ `'   /|   `-'  /           \n" +
                " |  |  \\    /  \\      /           \n" +
                " ''-'   `'-'    `-..-'              ");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
        return builder.sources(AppRun.class);
    }
}