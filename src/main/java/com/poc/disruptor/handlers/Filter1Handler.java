package com.poc.disruptor.handlers;

import com.poc.disruptor.config.DisruptorEvent;
import com.poc.disruptor.Route;

/**
 * Created by prabath on 12/24/16.
 */
public class Filter1Handler extends DisruptorEventHandler{


    public void onEvent(DisruptorEvent carbonDisruptorEvent, long l, boolean b) throws Exception {
        Route msg = (Route) carbonDisruptorEvent.getEvent();
        if(msg.getAge() % 2 == 0 && !msg.isFilterOneExecutes()) {
            System.out.println("===========1111==========" + msg.getName() + "======" + msg.getAge());
            msg.setFilterOneExecutes(true);
            Thread.sleep(300);
        } else {
            msg.setValid(false);
        }
    }

    public void onEvent(Object o) throws Exception {

        if(o instanceof DisruptorEvent) {
            Route msg = (Route) ((DisruptorEvent)o).getEvent();
            System.out.println("========1111222=========" + msg.getName() + "======" + msg.getAge());
        }

    }
}
