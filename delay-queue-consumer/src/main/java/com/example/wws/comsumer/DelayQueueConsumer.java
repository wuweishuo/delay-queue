package com.example.wws.comsumer;

import com.example.wws.core.client.DelayQueueClient;
import com.example.wws.core.domain.Message;

/**
 * @author wws
 * @version 1.0.0
 * @date 2020-08-07 15:55
 **/
public class DelayQueueConsumer {

    private String host;

    private int port;

    private DelayQueueClient client;

    private String topic;

    public DelayQueueConsumer(String host, int port, String topic) {
        this.host = host;
        this.port = port;
        client = new DelayQueueClient(host, port);
        this.topic = topic;
    }

    public Message get(){
        return client.get(topic);
    }

}
