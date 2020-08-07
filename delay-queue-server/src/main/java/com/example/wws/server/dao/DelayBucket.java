package com.example.wws.server.dao;

import com.example.wws.server.domain.Job;

import java.util.*;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author wws
 * @version 1.0.0
 * @date 2020-08-07 10:39
 **/
public class DelayBucket {

    private static final int DEFAULT_SIZE = 8;
    private int size;
    private List<DelayQueue<Item>> bucket;

    public DelayBucket() {
        this(DEFAULT_SIZE);
    }

    public DelayBucket(int size) {
        this.size = size;
        bucket = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            bucket.add(new DelayQueue<>());
        }
    }

    public boolean push(Long id, Job job){
        int i = (int) (id & size - 1);
        DelayQueue<Item> queue = bucket.get(i);
        queue.put(new Item(id, job.getDelay()));
        return true;
    }

    public List<Long> poll() {
        List<Long> list = new ArrayList<>(bucket.size());
        for (DelayQueue queue: bucket) {
            Delayed delayed = queue.poll();
            if(delayed != null){
                Item item = (Item) delayed;
                list.add(item.id);
            }
        }
        return list;
    }

    public int getSize() {
        return size;
    }

    private class Item implements Delayed {

        Long id;

        Long delay;

        Item(Long id, Long delay) {
            this.id = id;
            this.delay = delay;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(delay - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            Item item = (Item) o;
            return this.delay.compareTo(item.delay);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Item item = (Item) o;
            return Objects.equals(id, item.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }
}
