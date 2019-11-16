package com.uanid.myserver.notibot.sender;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author uanid
 * @since 2019-11-14
 */
@Async
@Configuration
public class SenderConfig extends AsyncConfigurerSupport {

    @Autowired
    private SenderService sendService;

    @Bean
    public Executor senderExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(16);
        taskExecutor.setQueueCapacity(512);
        taskExecutor.setThreadNamePrefix("Sender-Thread-");
        taskExecutor.initialize();
//        taskExecutor.setRejectedExecutionHandler();
        return taskExecutor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return super.getAsyncUncaughtExceptionHandler();
    }
}
