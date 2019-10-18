package com.projekat.eObrazovanje.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class GazepointThreadConfig {
	
	@Bean
    public TaskExecutor gazepointTaskExecutor() {
		
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(1);
        executor.setThreadNamePrefix("gazepointThreadTaskExecutor");
        executor.initialize();
 
        return executor;
    }
}
