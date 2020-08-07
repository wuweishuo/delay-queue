package com.example.wws.core.client;

import com.example.wws.core.domain.Message;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author wws
 * @version 1.0.0
 * @date 2020-08-07 15:45
 **/
public class DelayQueueClient {

    private RestTemplate restTemplate;

    private String host;

    private int port;

    public DelayQueueClient(String host, int port) {
        this.host = host;
        this.port = port;
        this.restTemplate = new RestTemplate();
    }

    public Message get(String topic){
        String uriString = UriComponentsBuilder.fromUriString("http://localhost:8080")
                .path("/message")
                .queryParam("topic", topic)
                .build().toUriString();
        return restTemplate.getForObject(uriString, Message.class);
    }

    public Boolean push(String topic, Message message){
        String uriString = UriComponentsBuilder.fromUriString("http://localhost:8080")
                .path("/message")
                .queryParam("topic", topic)
                .build().toUriString();
        return restTemplate.postForObject(uriString, message, Boolean.class);
    }

}
