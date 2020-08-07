package com.example.wws.server.controller;

import com.example.wws.core.domain.Message;
import com.example.wws.server.dao.DelayBucket;
import com.example.wws.server.dao.JobMap;
import com.example.wws.server.dao.ReadyQueueMap;
import com.example.wws.server.domain.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wws
 * @version 1.0.0
 * @date 2020-08-07 11:37
 **/
@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private JobMap jobMap;
    @Autowired
    private ReadyQueueMap readyQueueMap;
    @Autowired
    private DelayBucket delayBucket;

    @GetMapping
    public Job get(String topic) {
        Long id = readyQueueMap.get(topic);
        if (id == null) {
            return null;
        }
        return jobMap.remove(id);
    }

    @PostMapping
    public boolean push(String topic, @RequestBody Message message) {
        Job job = jobMap.put(topic, message);
        return delayBucket.push(job.getId(), job);
    }

}
