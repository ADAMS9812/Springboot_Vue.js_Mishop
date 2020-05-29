package com.ysw;

import com.ysw.entity.UUser;
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

    //注释
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MishopApplication.class);
    }

}

//    打war包用的
//    extends SpringBootServletInitializer
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(MishopApplication.class);
//    }