package com.duzhuo.wansystem.config;

import org.hibernate.cfg.ImprovedNamingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.ValidationMode;
import javax.sql.DataSource;
import javax.persistence.EntityManager;
import java.util.Properties;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/12/2 13:50
 */


@Configuration
@EnableTransactionManagement
public class EntityManageConfig {

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Primary
    @Bean(name = "entityManagerPrimary")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder){
        return entityManageFactory(builder).getObject().createEntityManager();
    }

    @Primary
    @Bean(name = "entityManageFactoryPrimary")
    public LocalContainerEntityManagerFactoryBean entityManageFactory(EntityManagerFactoryBuilder builder){
        LocalContainerEntityManagerFactoryBean entityManagerFactory =  builder.dataSource(dataSource)
                .packages("com.duzhuo.wansystem.entity")
                .persistenceUnit("persistenceUnit")
                .build();
        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.show_sql", "false");
        // 字段命名规约 驼峰转_
        jpaProperties.put("hibernate.physical_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
        jpaProperties.put("hibernate.connection.charSet", "utf-8");
        entityManagerFactory.setJpaProperties(jpaProperties);
        // 防止save方法出发校验
        entityManagerFactory.setValidationMode(ValidationMode.NONE);
        return entityManagerFactory;
    }

    @Primary
    @Bean(name = "transactionManagerPrimary")
    public PlatformTransactionManager transactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManageFactory(builder).getObject());
    }

}