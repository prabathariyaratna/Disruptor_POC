package com.poc.disruptor;

import com.lmax.disruptor.dsl.Disruptor;
import com.poc.disruptor.config.Constants;
import com.poc.disruptor.config.DisruptorConfig;
import com.poc.disruptor.config.RouteConfigContext;
import com.poc.disruptor.handlers.*;
import com.poc.disruptor.publisher.DisruptorEventPublisher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

/**
 * Created by prabath on 12/24/16.
 */
public class DisruptorInitiator {


    public static void main(String args[]) {

        Map<String, String> parameters = new HashMap<>();


        DisruptorConfig disruptorConfig = new DisruptorConfig(parameters
                .getOrDefault(Constants.DISRUPTOR_BUFFER_SIZE, Constants.DEFAULT_DISRUPTOR_BUFFER_SIZE),
                parameters.getOrDefault(Constants.DISRUPTOR_COUNT, Constants.DEFAULT_DISRUPTOR_COUNT),
                parameters.getOrDefault(Constants.DISRUPTOR_EVENT_HANDLER_COUNT,
                        Constants.DEFAULT_DISRUPTOR_EVENT_HANDLER_COUNT),
                parameters.getOrDefault(Constants.WAIT_STRATEGY, Constants.DEFAULT_WAIT_STRATEGY),
                Boolean.parseBoolean(parameters.getOrDefault(Constants.SHARE_DISRUPTOR_WITH_OUTBOUND,
                        Constants.DEFAULT_SHARE_DISRUPTOR_WITH_OUTBOUND)), parameters
                .getOrDefault(Constants.DISRUPTOR_CONSUMER_EXTERNAL_WORKER_POOL,
                        Constants.DEFAULT_DISRUPTOR_CONSUMER_EXTERNAL_WORKER_POOL));
        List<DisruptorEventHandler> eventHandlers = new ArrayList<>();
        eventHandlers.add(new Filter1Handler());
        eventHandlers.add(new Filter1Handler());
        eventHandlers.add(new Filter1Handler());
        eventHandlers.add(new Filter2Handler());
        eventHandlers.add(new Filter3Handler());
        eventHandlers.add(new Filter4Handler());

        disruptorConfig.setEventHandlers(eventHandlers);
        DisruptorFactory.createDisruptors(DisruptorFactory.DisruptorType.INBOUND, disruptorConfig);


        DisruptorConfig disruptorConfigure = DisruptorFactory.getDisruptorConfig(DisruptorFactory.DisruptorType.INBOUND);
        Disruptor disruptor = disruptorConfigure.getDisruptor();

        disruptor.handleEventsWith(new Filter1Handler(), new Filter1Handler(),new Filter1Handler()).then(new Filter2Handler(), new Filter2Handler(), new Filter3Handler(), new Filter4Handler());
        disruptor.start();
        RouteConfigContext configContext = new RouteConfigContext();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        long lStartTime = System.nanoTime();
        for(int i = 0; i < 100; i++) {

            configContext.setTotalCount(100);
            Route msg = new Route("prabath" + i,i, configContext, 4);
            msg.setCountDownLatch(countDownLatch);
            disruptor.publishEvent(new DisruptorEventPublisher(msg));
        }

        System.out.println("=============================== Taking Semaphore ======================================");
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("=============================== took Semaphore ======================================");
        long lEndTime = System.nanoTime();
        long output = lEndTime - lStartTime;
        disruptor.shutdown();
        System.out.println("=============================== END ===================================Milliseconds===" + output / 1000000);
    }
}

