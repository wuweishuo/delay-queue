package com.example.wws.core.domain;

import lombok.Data;

/**
 * @author wws
 * @version 1.0.0
 * @date 2020-08-07 10:13
 **/
@Data
public class Message {

    private Long delay;

    private Integer totalRetry;

    private Long retryTime;

    private String body;

}
