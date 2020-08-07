package com.example.wws.server.dao;

import com.example.wws.core.domain.Message;
import com.example.wws.server.domain.Job;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author wws
 * @version 1.0.0
 * @date 2020-08-07 10:34
 **/
public class JobMap {

    private ConcurrentHashMap<Long, Job> map = new ConcurrentHashMap<>();

    private AtomicLong idGen = new AtomicLong(1);

    public Job put(String topic, Message message) {
        long id = idGen.getAndIncrement();
        Job job = new Job(id, topic, message);
        map.put(id, job);
        return job;
    }

    public Job get(Long id) {
        return map.get(id);
    }

    public Job remove(Long id) {
        return map.remove(id);
    }

    public int size(){
        return map.size();
    }

}
