package com.pluralsight.NorthwindTradersAPI.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    private BasicDataSource dataSource;

    @Bean
    public DataSource dataSource(){
        return dataSource;
    }

    public DatabaseConfig(@Value("${datasource.url}") String url){
        String username = System.getProperty("dbUsername");
        String password = System.getProperty("dbPassword");

        dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
    }

}
