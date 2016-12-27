package com.poc.disruptor.config;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.poc.disruptor.handlers.DisruptorEventHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by prabath on 12/25/16.
 */
public class DisruptorConfig {

    private int bufferSize;
    private int noDisruptors;
    private int noOfEventHandlersPerDisruptor;
    private int noOfThreadsInConsumerWorkerPool;
    private String disruptorWaitStrategy;
    private boolean notShared;
    private List<Disruptor> disruptorMap = new ArrayList<Disruptor>();
    private AtomicInteger index = new AtomicInteger(0);
    private List<DisruptorEventHandler> eventHandlers;

    public DisruptorConfig() {

        this.bufferSize = 512;

        this.noDisruptors = 5;

        this.noOfEventHandlersPerDisruptor = 1;

        this.noOfThreadsInConsumerWorkerPool = 0;

        this.disruptorWaitStrategy = Constants.PHASED_BACKOFF;
    }

    public DisruptorConfig(String bufferSize, String noDisruptors, String noOfEventHandlersPerDisruptor,
                           String disruptorWaitStrategy, boolean notShared, String noOfThreadsInConsumerWorkerPool) {
        this.bufferSize = Integer.parseInt(bufferSize);
        this.noDisruptors = Integer.parseInt(noDisruptors);
        this.noOfEventHandlersPerDisruptor = Integer.parseInt(noOfEventHandlersPerDisruptor);
        this.noOfThreadsInConsumerWorkerPool = Integer.parseInt(noOfThreadsInConsumerWorkerPool);
        this.disruptorWaitStrategy = disruptorWaitStrategy;
        this.notShared = notShared;

    }

    public int getBufferSize() {
        return bufferSize;
    }

    public int getNoDisruptors() {
        return noDisruptors;
    }

    /*public int getNoOfEventHandlersPerDisruptor() {
        return noOfEventHandlersPerDisruptor;
    }*/

    public String getDisruptorWaitStrategy() {
        return disruptorWaitStrategy;
    }

    public boolean isShared() {
        return !notShared;
    }

    public Disruptor getDisruptor() {
        int ind = index.getAndIncrement() % noDisruptors;
        return disruptorMap.get(ind);
    }

    public void addDisruptor(Disruptor disruptor) {
        disruptorMap.add(disruptor);
    }

    public void notifyChannelInactive() {
        index.getAndDecrement();
    }

    public int getNoOfThreadsInConsumerWorkerPool() {
        return noOfThreadsInConsumerWorkerPool;
    }

    public List<DisruptorEventHandler> getEventHandlers() {
        return eventHandlers;
    }

    public void setEventHandlers(List<DisruptorEventHandler> eventHandlers) {
        this.eventHandlers = eventHandlers;
    }

    public int getNoOfEventHandlersPerDisruptor() {
        if(eventHandlers == null || eventHandlers.size() == 0) {
            return 0;
        }

        return eventHandlers.size();
    }
}
