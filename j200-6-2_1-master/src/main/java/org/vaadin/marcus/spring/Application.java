package org.vaadin.marcus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication  //标记这是一个Spring Boot应用程序的主类
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);//Spring Boot应用程序的启动方法
    }
}  //表示一个基本的Spring Boot应用程序入口类，用于启动和运行Spring Boot应用程序
