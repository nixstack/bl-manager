package com.nixstack.service.course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

// 因为bl-base-model模块中存在spring-boot-starter-data-mongodb的依赖，springboot会在项目启动时自动实例化一个mongo实例，因此需要在项目启动时禁用mongo的自动配置
// 在Springboot启动类上增加 @SpringBootApplication(exclude = MongoAutoConfiguration.class) 配置
@SpringBootApplication(exclude = MongoAutoConfiguration.class)
// Error creating bean with name 'courseBaseDao': Invocation of init method failed; nested exception is java.lang.IllegalArgumentException: Not a managed type: class com.nixstack.base.model.course.entity.CourseBase
@EntityScan("com.nixstack.base.model.course") //扫描实体类
@ComponentScan(basePackages = {"com.nixstack.base.api", "com.nixstack.service.course", "com.nixstack.base.common"}) // 扫描api和本模块，必需要加，否则swagger-ui不能正常加载
@EnableDiscoveryClient
// No qualifying bean of type 'com.nixstack.service.course.client.CmsPageClient' available: expected at least 1 bean which qualifies as autowire candidate.
// Dependency annotations: {@org.springframework.beans.factory.annotation.Autowired(required=true)}
@EnableFeignClients
@EnableZuulProxy
public class CourseApplication {
    public static void main(String[] args) {
        SpringApplication.run(CourseApplication.class, args);
    }
}
