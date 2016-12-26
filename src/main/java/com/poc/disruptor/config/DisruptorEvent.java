package com.poc.disruptor.config;

import com.lmax.disruptor.EventFactory;

/**
 * Created by prabath on 12/25/16.
 */
public class DisruptorEvent  {


    public static final EventFactory<DisruptorEvent> EVENT_FACTORY = DisruptorEvent::new;
    private Object event;

    public DisruptorEvent() {
    }

    public Object getEvent() {
        return event;
    }

    public void setEvent(Object event) {
        this.event = event;
    }

}