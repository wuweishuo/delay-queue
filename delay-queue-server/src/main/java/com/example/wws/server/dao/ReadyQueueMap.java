package com.example.wws.server.dao;

import com.example.wws.server.domain.Job;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author wws
 * @version 1.0.0
 * @date 2020-08-07 10:50
 **/
public class ReadyQueueMap {

    private ConcurrentHashMap<String, LinkedBlockingQueue<Long>> topicMap = new ConcurrentHashMap<>();

    public boolean push(Job job) throws InterruptedException {
        String topic = job.getTopic();
        Long id = job.getId();
        LinkedBlockingQueue<Long> linkedBlockingQueue = topicMap.computeIfAbsent(topic, f -> new LinkedBlockingQueue<>());
        linkedBlockingQueue.put(id);
        return true;
    }

    public Long get(String topic){
        LinkedBlockingQueue<Long> linkedBlockingQueue = topicMap.get(topic);
        if(linkedBlockingQueue == null){
            return null;
        }
        return linkedBlockingQueue.poll();
    }

}
