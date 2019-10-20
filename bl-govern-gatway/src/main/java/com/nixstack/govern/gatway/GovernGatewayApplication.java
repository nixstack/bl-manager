package com.nixstack.govern.gatway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, MongoAutoConfiguration.class})
@EnableZuulProxy
@ComponentScan(basePackages = {"com.nixstack.base.api", "com.nixstack.base.common"})
public class GovernGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GovernGatewayApplication.class, args);
    }
}
