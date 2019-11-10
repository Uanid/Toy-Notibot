package com.uanid.myserver.notibot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.telegram.telegrambots.ApiContextInitializer;

@Slf4j
@SpringBootApplication
public class NotibotApplication {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(NotibotApplication.class, args);
    }
    
}
