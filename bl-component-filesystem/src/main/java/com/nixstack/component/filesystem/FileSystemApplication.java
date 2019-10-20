package com.nixstack.component.filesystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
// swagger-ui: "com.nixstack.base.api", "com.nixstack.component.filesystem",
// 异常捕获： "com.nixstack.base.common"
@ComponentScan(basePackages = {"com.nixstack.base.api", "com.nixstack.component.filesystem", "com.nixstack.base.common"})
//@CrossOrigin
@EnableDiscoveryClient
@EnableZuulProxy // Swagger
public class FileSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(FileSystemApplication.class, args);
    }
}
