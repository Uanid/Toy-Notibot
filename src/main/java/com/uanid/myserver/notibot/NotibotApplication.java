package com.uanid.myserver.notibot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.mail.ImapIdleChannelAdapter;
import org.springframework.integration.mail.ImapMailReceiver;
import org.springframework.integration.mail.SearchTermStrategy;
import org.springframework.integration.mail.config.ImapIdleChannelAdapterParser;
import org.springframework.integration.mail.dsl.Mail;

import java.lang.reflect.Field;

@Slf4j
@SpringBootApplication
public class NotibotApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotibotApplication.class, args);
    }

  
}
