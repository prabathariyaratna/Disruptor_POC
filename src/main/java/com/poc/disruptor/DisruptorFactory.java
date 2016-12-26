package com.poc.disruptor;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.poc.disruptor.config.Constants;
import com.poc.disruptor.config.DisruptorConfig;
import com.poc.disruptor.config.DisruptorEvent;
import com.poc.disruptor.exception.GenericExceptionHandler;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by prabath on 12/24/16.
 */
public class DisruptorFactory {

    private static ConcurrentHashMap<DisruptorType, DisruptorConfig> disruptorConfigHashMap = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public static void createDisruptors(DisruptorType type, DisruptorConfig disruptorConfig) {
        WaitStrategy inboundWaitStrategy = getWaitStrategy(disruptorConfig.getDisruptorWaitStrategy());
        ExecutorService executorService = getExecutorService(disruptorConfig);

        for (int i = 0; i < disruptorConfig.getNoDisruptors(); i++) {

            Disruptor disruptor = new Disruptor<>(DisruptorEvent.EVENT_FACTORY, disruptorConfig.getBufferSize(),
                    executorService,
                    ProducerType.SINGLE,
                    inboundWaitStrategy);
            ExceptionHandler exh = new GenericExceptionHandler();

                disruptor.handleEventsWith(disruptorConfig.getEventHandlers().toArray(new EventHandler[disruptorConfig.getEventHandlers().size()]));
                for (EventHandler eventHandler : disruptorConfig.getEventHandlers()) {
                    disruptor.handleExceptionsFor(eventHandler).with(exh);
                }

            disruptorConfig.addDisruptor(disruptor.start());
        }
        disruptorConfigHashMap.put(type, disruptorConfig);
    }


    private static WaitStrategy getWaitStrategy(String waitstrategy) {
        WaitStrategy waitStrategy;
        switch (waitstrategy) {
            case Constants.BLOCKING_WAIT:
                waitStrategy = new BlockingWaitStrategy();
                break;
            case Constants.BUSY_SPIN:
                waitStrategy = new BusySpinWaitStrategy();
                break;
            case Constants.LITE_BLOCKING:
                waitStrategy = new LiteBlockingWaitStrategy();
                break;
            case Constants.SLEEP_WAITING:
                waitStrategy = new SleepingWaitStrategy();
                break;
            case Constants.TIME_BLOCKING:
                waitStrategy = new TimeoutBlockingWaitStrategy(1, TimeUnit.SECONDS);
                break;
            default:
                waitStrategy = PhasedBackoffWaitStrategy.withLiteLock(1, 4, TimeUnit.SECONDS);

        }
        return waitStrategy;
    }


    public static DisruptorConfig getDisruptorConfig(DisruptorType disruptorType) {
        return disruptorConfigHashMap.get(disruptorType);
    }

    /**
     * Describe types of disruptors.
     */
    public enum DisruptorType {
        INBOUND, OUTBOUND
    }


    private static ExecutorService getExecutorService(DisruptorConfig disruptorConfig) {
        int noOfEventHandlerPerDisruptor = disruptorConfig.getNoOfEventHandlersPerDisruptor();
        int disruptorCount = disruptorConfig.getNoDisruptors();

        if(noOfEventHandlerPerDisruptor <= 0 || disruptorCount <= 0) {
            //Handle exception
        }

        int totalThreadCount = noOfEventHandlerPerDisruptor * disruptorCount;
        return Executors.newFixedThreadPool(totalThreadCount);
    }
}
