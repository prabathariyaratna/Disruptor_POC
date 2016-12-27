package com.poc.disruptor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
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

        disruptor.handleEventsWith(new Filter1Handler()).then(new Filter2Handler(), new Filter3Handler(), new Filter4Handler());
        disruptor.start();

        RouteConfigContext configContext = new RouteConfigContext();
        configContext.setTotalCount(10);
        for(int i = 0; i < 10; i++) {
            Route msg = new Route("prabath" + i,i, configContext, 4);
            disruptor.publishEvent(new DisruptorEventPublisher(msg));
        }
    }
}

