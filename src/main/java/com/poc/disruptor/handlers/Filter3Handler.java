package com.poc.disruptor.handlers;

import com.poc.disruptor.config.DisruptorEvent;
import com.poc.disruptor.Route;

/**
 * Created by prabath on 12/24/16.
 */
public class Filter3Handler extends DisruptorEventHandler{


    public void onEvent(DisruptorEvent carbonDisruptorEvent, long l, boolean b) throws Exception {
        Route msg = (Route) carbonDisruptorEvent.getEvent();
        if(msg.canExecute() && msg.getAge() % 8 == 0) {
            System.out.println("===========33333==========" + msg.getName() + "======" + msg.getAge());
        } else {
            msg.setValid(false);
        }
    }

    public void onEvent(Object o) throws Exception {

        if(o instanceof DisruptorEvent) {
            Route msg = (Route) ((DisruptorEvent)o).getEvent();
            System.out.println("========33333=========" + msg.getName() + "======" + msg.getAge());
        }

    }
}

