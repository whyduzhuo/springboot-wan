package com.duzhuo.wansystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.ValidationMode;
import javax.sql.DataSource;
import java.util.Map;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/12/2 13:50
 */


@Configuration
@EnableTransactionManagement
public class EntityManageConfig {

    @Autowired
    private JpaProperties jpaProperties;
    @Autowired
    private HibernateProperties hibernateProperties;
    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Primary
    @Bean(name = "entityManagerPrimary")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder){
        return entityManageFactory(builder).getObject().createEntityManager();
    }

    @Bean(name = "hibernateProperties")
    public Map<String, Object> getHibernateProperties() {
        return hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
    }

    @Primary
    @Bean(name = "entityManageFactoryPrimary")
    public LocalContainerEntityManagerFactoryBean entityManageFactory(EntityManagerFactoryBuilder builder){
        LocalContainerEntityManagerFactoryBean entityManagerFactory =  builder.dataSource(dataSource)
                .packages("com.duzhuo.wansystem.entity")
                .persistenceUnit("persistenceUnit")
                .properties(getHibernateProperties())
                .build();
        // 防止save方法触发校验
        entityManagerFactory.setValidationMode(ValidationMode.NONE);
        return entityManagerFactory;
    }

    @Primary
    @Bean(name = "transactionManagerPrimary")
    public PlatformTransactionManager transactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManageFactory(builder).getObject());
    }

}