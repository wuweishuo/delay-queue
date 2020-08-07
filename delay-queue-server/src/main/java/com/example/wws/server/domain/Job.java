package com.example.wws.server.domain;

import com.example.wws.core.domain.Message;
import lombok.Data;

/**
 * @author wws
 * @version 1.0.0
 * @date 2020-08-07 10:56
 **/
@Data
public class Job {

    private Long id;

    private String topic;

    private Integer retry;

    private Long delay;

    private Integer totalRetry;

    private Long retryTime;

    private String body;

    public Job(Long id, String topic, Message message) {
        this.id = id;
        this.topic = topic;
        this.retry = 0;
        this.delay = message.getDelay();
        this.totalRetry = message.getTotalRetry();
        this.retryTime = message.getRetryTime();
        this.body = message.getBody();
    }
}
