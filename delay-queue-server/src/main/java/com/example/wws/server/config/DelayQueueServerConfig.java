package com.example.wws.server.config;

import com.example.wws.server.dao.DelayBucket;
import com.example.wws.server.dao.JobMap;
import com.example.wws.server.dao.ReadyQueueMap;
import com.example.wws.server.schedule.DefaultDispatcher;
import com.example.wws.server.schedule.Dispatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wws
 * @version 1.0.0
 * @date 2020-08-07 11:45
 **/
@Configuration
public class DelayQueueServerConfig {

    @Bean
    public JobMap jobMap(){
        return new JobMap();
    }

    @Bean
    public ReadyQueueMap readyQueueMap(){
        return new ReadyQueueMap();
    }

    @Bean
    public DelayBucket delayBucket(){
        return new DelayBucket(8);
    }

    @Bean
    public Dispatcher dispatcher(JobMap jobMap, ReadyQueueMap readyQueueMap, DelayBucket delayBucket){
        DefaultDispatcher defaultDispatcher = new DefaultDispatcher(delayBucket, jobMap, readyQueueMap);
        defaultDispatcher.start();
        return defaultDispatcher;
    }

}
