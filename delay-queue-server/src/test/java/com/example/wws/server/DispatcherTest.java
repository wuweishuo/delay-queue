package com.example.wws.server;

import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wws
 * @version 1.0.0
 * @date 2020-08-07 14:28
 **/
public class DispatcherTest {

    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {

        private AtomicInteger i = new AtomicInteger();

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "dispatcher-thread-"+i.get());
        }
    });


    @Test
    public void test() throws InterruptedException {
//        ScheduledExecutorService service = Executors.newScheduledThreadPool(5);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("task finish time: " + System.currentTimeMillis());
        }, 1000L, 1000L, TimeUnit.MILLISECONDS);

        System.out.println("schedule finish time: " + System.currentTimeMillis());
        while (true) {
        }
    }

}
