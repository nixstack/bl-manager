package com.nixstack.base.api.config;

import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
@Primary
public class Swagger2Configuration implements SwaggerResourcesProvider {
    //是否开启swagger，正式环境一般是需要关闭的，可根据springboot的多环境配置进行设置
//    Boolean swaggerEnabled;

//    @Autowired
//    RouteLocator routeLocator;

    // 需要在启动应用中加上@EnableZuulProxy
    // 否则会报Field routeLocator in com.required a bean of type 'org.springframework.cloud.netflix.zuul.filters.RouteLocator' that could not be found
    @Autowired
    SimpleRouteLocator simpleRouteLocator;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.nixstack"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("无涯api文档")
                .description("前后端分离，供各端api接口查阅")
                .version("1.0")
                .build();
    }

    public List<SwaggerResource> get() {
        //利用routeLocator动态引入微服务
        List<SwaggerResource> resources = new ArrayList();
        resources.add(swaggerResource("zuul-gateway","/v2/api-docs","1.0"));
        //循环 使用Lambda表达式简化代码
        simpleRouteLocator.getRoutes().forEach(route ->{
            if (route.getId().startsWith("bl-")) {
                //动态获取，过虑掉无效项
                resources.add(swaggerResource(route.getId(),route.getFullPath().replace("**", "v2/api-docs"), "1.0"));
            }
        });

//        routeLocator.getRoutes().forEach(route ->{
//            //动态获取
//            resources.add(swaggerResource(route.getId(),route.getFullPath().replace("**", "v2/api-docs"), "1.0"));
//        });

        return resources;

//        return null;
    }

    private SwaggerResource swaggerResource(String name,String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }
}
