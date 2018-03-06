package com.stampyt.hello;

import org.postgresql.Driver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class JdbcConnection {


    @Bean
    public DriverManagerDataSource dataSource() throws URISyntaxException {

        DriverManagerDataSource basicDataSource = new DriverManagerDataSource();

        if(System.getenv("LOCAL") != null && System.getenv("LOCAL") .equals("true")){

            basicDataSource.setUrl("jdbc:h2:./mem");
            basicDataSource.setDriverClassName(org.h2.Driver.class.getName());
            basicDataSource.setUsername("sa");
            basicDataSource.setPassword("");

        } else {
            URI dbUri = new URI(System.getenv("DATABASE_URL"));

            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
            //dbUrl += "?sslmode=require&user=loic.dalzotto@hotmail.fr&password=Abc01234";

            basicDataSource.setUrl(dbUrl);
            basicDataSource.setDriverClassName(Driver.class.getName());
            basicDataSource.setUsername(username);
            basicDataSource.setPassword(password);
        }

        return basicDataSource;
    }
}
