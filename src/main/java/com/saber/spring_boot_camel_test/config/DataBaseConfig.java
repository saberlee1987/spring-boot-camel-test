package com.saber.spring_boot_camel_test.config;

import org.apache.camel.component.sql.SqlComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataBaseConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public SqlComponent sqlComponent() {
        SqlComponent sqlComponent = new SqlComponent();
        sqlComponent.setDataSource(dataSource);
        return sqlComponent;
    }
}
