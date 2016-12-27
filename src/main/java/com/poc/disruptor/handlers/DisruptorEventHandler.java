package com.poc.disruptor.handlers;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.poc.disruptor.config.DisruptorEvent;

/**
 * Abstract class of Disruptor consumers.
 */
public abstract class DisruptorEventHandler implements EventHandler<DisruptorEvent>, WorkHandler {

    private Disruptor disruptor;

    public abstract void onEvent(DisruptorEvent carbonDisruptorEvent, long l, boolean b) throws Exception;

    public abstract void onEvent(Object o) throws Exception;

    public Disruptor getDisruptor() {
        return disruptor;
    }

    public void setDisruptor(Disruptor disruptor) {
        this.disruptor = disruptor;
    }
}
