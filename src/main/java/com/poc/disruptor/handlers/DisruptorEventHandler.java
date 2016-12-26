package com.poc.disruptor.handlers;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import com.poc.disruptor.config.DisruptorEvent;

/**
 * Abstract class of Disruptor consumers.
 */
public abstract class DisruptorEventHandler implements EventHandler<DisruptorEvent>, WorkHandler {

    public abstract void onEvent(DisruptorEvent carbonDisruptorEvent, long l, boolean b) throws Exception;

    public abstract void onEvent(Object o) throws Exception;
}
