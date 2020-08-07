package com.example.wws.server.schedule;

import com.example.wws.server.dao.DelayBucket;
import com.example.wws.server.dao.JobMap;
import com.example.wws.server.dao.ReadyQueueMap;
import com.example.wws.server.domain.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wws
 * @version 1.0.0
 * @date 2020-08-07 10:25
 **/
public class DefaultDispatcher implements Dispatcher {

    private DelayBucket delayBucket;

    private JobMap jobMap;

    private ReadyQueueMap readyQueueMap;

    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {

        private AtomicInteger i = new AtomicInteger();

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "dispatcher-thread-" + i.get());
        }
    });

    private Logger logger = LoggerFactory.getLogger(DefaultDispatcher.class);

    public DefaultDispatcher(DelayBucket delayBucket, JobMap jobMap, ReadyQueueMap readyQueueMap) {
        this.delayBucket = delayBucket;
        this.jobMap = jobMap;
        this.readyQueueMap = readyQueueMap;
    }

    @Override
    public void start() {
        scheduledExecutorService.scheduleAtFixedRate(new DispatcherThread(), 1, 1, TimeUnit.SECONDS);
    }

    private class DispatcherThread implements Runnable {

        @Override
        public void run() {
            List<Long> ids = delayBucket.poll();
            logger.info("job size:{}, delay id;{}", jobMap.size(), ids);
            for (Long id : ids) {
                Job job = jobMap.get(id);
                logger.info("dispatching job:{}", job);
                if (job.getDelay() < System.currentTimeMillis()) {
                    try {
                        readyQueueMap.push(job);
                        logger.info("dispatch ok job:{}", job);
                    } catch (InterruptedException e) {
                        logger.info("dispatch err job:{}, e:{}", job, e);
                    }
                }
            }
        }
    }
}
