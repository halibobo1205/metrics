package com.halibobo.metrics;

import com.halibobo.metrics.service.MService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j(topic = "app")
@SpringBootApplication
public class App {
    @Autowired
    private ApplicationContext ctx;
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(App.class, args);
        context.getBean(MService.class).start();
    }
}
