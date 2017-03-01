package com.poc.disruptor.handlers;

import com.lmax.disruptor.RingBuffer;
import com.poc.disruptor.config.DisruptorEvent;
import com.poc.disruptor.Route;

/**
 * Created by prabath on 12/24/16.
 */
public class Filter2Handler extends DisruptorEventHandler{

    private static final String NAME = "filter2";

    public void onEvent(DisruptorEvent carbonDisruptorEvent, long l, boolean b) throws Exception {
        Route msg = (Route) carbonDisruptorEvent.getEvent();
        if(msg.getAge()% 2 == 0 && msg.isValid()) {
            //System.out.println("===========22222==========" + msg.getName() + "======" + msg.getAge());
            msg.visited(NAME);
            msg.setValid(true);
        } else {
            msg.setValid(false);
            if(msg.isValid()) {
                msg.getConfigContext().increaseMissCount();
            }
        }

        if(msg.isFinalized()) {
            msg.getConfigContext().addFinalizeRoutes(msg);
           // System.out.println("===========2222== Finalize========" + msg.getName() + "======" + msg.getAge()+ "===== finalized count ===" + msg.getConfigContext().getFinalizeRoutes().size() + "=== missed count ===" + msg.getConfigContext().getMissCount());
        }

        if(msg.getConfigContext().isComplete()) {
            System.out.println("===========Finished========");
            msg.getCountDownLatch().countDown();
            //System.out.println("===============================Sent single to semaphore=================================");
        }
    }

    public void onEvent(Object o) throws Exception {

        if(o instanceof DisruptorEvent) {
            Route msg = (Route) ((DisruptorEvent)o).getEvent();
           // System.out.println("========22222=========" + msg.getName() + "======" + msg.getAge());
        }

    }


}

