package com.rivers.microservicecoursemanagement.config;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {

    @Autowired
    private Environment environment;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean icemfb = new LocalContainerEntityManagerFactoryBean();

        icemfb.setJpaVendorAdapter(getJpaVendorAdapter());
        icemfb.setDataSource(dataSource());
        icemfb.setPersistenceUnitName("entityManagerFactory");
        icemfb.setPackagesToScan("com.rivers.microservicecoursemanagement.model");
        icemfb.setJpaProperties(hibernateProperties());

        return icemfb;
    }

    @Bean
    public JpaVendorAdapter getJpaVendorAdapter() {
        JpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        return adapter;
    }

    @Bean
    public DataSource dataSource(){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getProperty("spring.datasource.driver-class-name"));
        dataSource.setUrl(environment.getProperty("spring.datasource.url"));
        dataSource.setUsername(environment.getProperty("spring.datasource.username"));
        dataSource.setPassword(environment.getProperty("spring.datasource.password"));
        return dataSource;
    }

    private final Properties hibernateProperties(){
        Properties properties = new Properties();

        properties.put("spring.jpa.properties.hibernate.dialect", environment.getProperty("spring.jpa.properties.hibernate.dialect"));
        properties.put("spring.jpa.properties.hibernate.show_sql", environment.getProperty("spring.jpa.properties.hibernate.show_sql"));
        properties.put("spring.jpa.properties.hibernate.format_sql", environment.getProperty("spring.jpa.properties.hibernate.format_sql"));
        properties.put("spring.jpa.properties.hibernate.hbm2ddl.auto", environment.getProperty("spring.jpa.properties.hibernate.hbm2ddl.auto"));
        properties.put("spring.jpa.properties.hibernate.max_fetch_depth", environment.getProperty("spring.jpa.properties.hibernate.max_fetch_depth"));
        properties.put("spring.jpa.properties.hibernate.cache.use_second_level_cache", environment.getProperty("spring.jpa.properties.hibernate.cache.use_second_level_cache"));
        properties.put("spring.jpa.properties.hibernate.cache.use_minimal_puts", environment.getProperty("spring.jpa.properties.hibernate.cache.use_minimal_puts"));
        properties.put("spring.jpa.properties.hibernate.connection_release_mode", environment.getProperty("spring.jpa.properties.hibernate.connection_release_mode"));
        properties.put("spring.jpa.properties.hibernate.cache.use_query_cache", environment.getProperty("spring.jpa.properties.hibernate.cache.use_query_cache"));

        return properties;
    }
}
