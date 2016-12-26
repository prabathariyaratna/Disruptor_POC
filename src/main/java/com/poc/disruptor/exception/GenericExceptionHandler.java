package com.poc.disruptor.exception;

import com.lmax.disruptor.ExceptionHandler;
//import org.slf4j.LoggerFactory;
/**
 * Created by prabath on 12/25/16.
 */
public class GenericExceptionHandler implements ExceptionHandler {
    //private static final Logger logger = LoggerFactory.getLogger(GenericExceptionHandler.class);


    public void handleEventException(Throwable ex, long sequence, Object event) {
        //logger.error("Caught unhandled exception while processing: " + event.toString(), ex);
    }

    public void handleOnStartException(Throwable ex) {

        //logger.error("Unexpected exception during startup.", ex);
    }

    public void handleOnShutdownException(Throwable ex) {
        //logger.error("Unexpected exception during shutdown.", ex);
    }
}