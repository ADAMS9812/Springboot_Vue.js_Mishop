package com.ysw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 引导类
 */

@SpringBootApplication
@MapperScan(basePackages = "com.ysw.dao")
@EnableSwagger2
public class MishopApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(MishopApplication.class, args);
    }

    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MishopApplication.class);
    }

    public static void mains(String[] args) {


        
        if (1 == 1) {




        }

    }

}

//    打war包用的
//    extends SpringBootServletInitializer
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(MishopApplication.class);
//    }