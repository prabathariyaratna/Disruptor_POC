package com.poc.disruptor.publisher;


import com.lmax.disruptor.EventTranslator;
import com.poc.disruptor.config.DisruptorEvent;

/**
 * Created by prabath on 12/25/16.
 */
public class DisruptorEventPublisher implements EventTranslator<DisruptorEvent> {

    private Object event;


    public DisruptorEventPublisher(Object event) {
        this.event = event;
    }

    public void translateTo(DisruptorEvent event, long sequence) {
        event.setEvent(this.event);
    }
}