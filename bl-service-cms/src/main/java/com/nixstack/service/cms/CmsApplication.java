package com.nixstack.service.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

// 因为bl-base-model模块中存在spring-boot-starter-data-jpa的依赖，springboot会在项目启动时自动连接数据库，因此需要在项目启动时禁用自动配置
// 在Springboot启动类上增加 @SpringBootApplication(exclude = DataSourceAutoConfiguration.class) 配置
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = {"com.nixstack.base.api", "com.nixstack.service.cms", "com.nixstack.base.common"}) // 扫描api，必需要加，否则swagger-ui不能加载
//@ComponentScan(basePackages = {"com.nixstack.service.cms"}) // 扫描本模块下的所有类
@EnableDiscoveryClient
@EnableZuulProxy
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    }
}
