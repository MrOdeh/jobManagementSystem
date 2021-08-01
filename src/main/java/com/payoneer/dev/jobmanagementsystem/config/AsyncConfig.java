package com.payoneer.dev.jobmanagementsystem.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@PropertySource("classpath:config.properties")
@Configuration
@EnableScheduling
@EnableAsync
public class AsyncConfig {
    // if the properties are not provided before then the default will be associated

    @Value("${thread.poolsize}")
    private Integer poolSize = 2;

    @Value("${thread.queuecapacity}")
    private Integer queueCapacity = 2;

    @Value("${thread.maxpoolsize}")
    private Integer maxPoolSize = 2;

    @Value("${thread.threadnameprefix}")
    private String threadPrefix = "worker";


    @Bean//(name = "taskExecutor")
    public Executor taskExecutor(){
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(poolSize);
        threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
        threadPoolTaskExecutor.setQueueCapacity(queueCapacity);
        threadPoolTaskExecutor.setThreadNamePrefix(String.valueOf(threadPrefix + "-"));
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }
}
