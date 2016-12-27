package com.poc.disruptor.handlers;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.poc.disruptor.config.DisruptorEvent;
import com.poc.disruptor.Route;

/**
 * Created by prabath on 12/24/16.
 */
public class Filter1Handler extends DisruptorEventHandler{


    private static final String NAME = "filter1";

    public void onEvent(DisruptorEvent carbonDisruptorEvent, long l, boolean b) throws Exception {
        Route msg = (Route) carbonDisruptorEvent.getEvent();
        if(msg.getAge() % 2 == 0 && !msg.isFilterOneExecutes()) {
            System.out.println("===========1111==========" + msg.getName() + "======" + msg.getAge());
            Thread.sleep(300);
            msg.visited(NAME);
            msg.setValid(true);
            msg.setFilterOneExecutes(true);
        } else {
            msg.setValid(false);
        }

        if(msg.isFinalized()) {
            msg.getConfigContext().addFinalizeRoutes(msg);
            System.out.println("===========1111== Finalize========" + msg.getName() + "======" + msg.getAge());
        }

        if(msg.getConfigContext().isComplete()) {
            System.out.println("===========Finished========");
        }
    }

    public void onEvent(Object o) throws Exception {

        if(o instanceof DisruptorEvent) {
            Route msg = (Route) ((DisruptorEvent)o).getEvent();
            System.out.println("========1111222=========" + msg.getName() + "======" + msg.getAge());
        }

    }

}
