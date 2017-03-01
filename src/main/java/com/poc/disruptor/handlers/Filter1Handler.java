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
        if(msg.getAge() % 2 == 0 && msg.isValid()) {
            //System.out.println("===========1111==========" + msg.getName() + "======" + msg.getAge());
            Thread.sleep(30);
            msg.visited(NAME);
            msg.setValid(true);
            msg.setFilterOneExecutes(true);
        } else {
            if(msg.isValid()) {
                msg.getConfigContext().increaseMissCount();
                msg.setValid(false);
            }
        }

        if(msg.isFinalized()) {
            msg.getConfigContext().addFinalizeRoutes(msg);
           // System.out.println("===========1111== Finalize========" + msg.getName() + "======" + msg.getAge()+ "===== finalized count ===" + msg.getConfigContext().getFinalizeRoutes().size() + "=== missed count ===" + msg.getConfigContext().getMissCount());
        }

        if(msg.getConfigContext().isComplete()) {
            System.out.println("===========Finished========");
            msg.getCountDownLatch().countDown();
           // System.out.println("===============================Sent single to semaphore=================================");

        }
    }

    public void onEvent(Object o) throws Exception {

        if(o instanceof DisruptorEvent) {
            Route msg = (Route) ((DisruptorEvent)o).getEvent();
            //System.out.println("========1111222=========" + msg.getName() + "======" + msg.getAge());
        }

    }

}
