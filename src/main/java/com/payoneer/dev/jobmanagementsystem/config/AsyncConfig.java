package com.payoneer.dev.jobmanagementsystem.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

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

    @Value("${thread.emailthreadnameprefix}")
    private String emailThreadPrefix = "worker";

    @Value("${thread.reminderthreadnameprefix}")
    private String reminderThreadPrefix = "worker";

    @Value("${thread.taskScheduler.poolsize}")
    private Integer taskSchedulerPoolSize = 2;

    // each job has its own threadpoolexecutor.

    @Bean(name = "RemindertaskExecutor")
    public Executor reminderTaskExecutor(){
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(poolSize);
        threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
        threadPoolTaskExecutor.setQueueCapacity(queueCapacity);
        threadPoolTaskExecutor.setThreadNamePrefix(String.valueOf(reminderThreadPrefix + "-"));
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

    @Bean(name = "EmailtaskExecutor")
    public Executor emailTaskExecutor(){
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(poolSize);
        threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
        threadPoolTaskExecutor.setQueueCapacity(queueCapacity);
        threadPoolTaskExecutor.setThreadNamePrefix(String.valueOf(emailThreadPrefix + "-"));
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

/*  all @Scheduled methods share a single thread by default
    I is possible to override this behavior by the below config */
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(taskSchedulerPoolSize);
        threadPoolTaskScheduler.setThreadNamePrefix("Scheduler-");
        threadPoolTaskScheduler.initialize();
        return threadPoolTaskScheduler;
    }

}
