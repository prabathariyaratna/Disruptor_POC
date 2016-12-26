package com.poc.disruptor.config;

/**
 * Created by prabath on 12/25/16.
 */
public final class Constants {

    // Disruptor related constants
    public static final String BUSY_SPIN = "BUSY_SPIN";

    public static final String BLOCKING_WAIT = "BLOCKING_WAIT";

    public static final String LITE_BLOCKING = "LITE_BLOCKING";

    public static final String PHASED_BACKOFF = "PHASED_BACKOFF";

    public static final String TIME_BLOCKING = "TIME_BLOCKING";

    public static final String SLEEP_WAITING = "SLEEP_WAITING";

    public static final String WAIT_STRATEGY = "disruptor.wait.strategy";

    public static final String DISRUPTOR_BUFFER_SIZE = "disruptor.buffer.size";

    public static final String DISRUPTOR_COUNT = "disruptor.count";

    public static final String DISRUPTOR_EVENT_HANDLER_COUNT = "disruptor.eventhandler.count";

    public static final String SHARE_DISRUPTOR_WITH_OUTBOUND = "share.disruptor.with.outbound";

    public static final int DEFAULT_EXECUTOR_WORKER_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    public static final String DEFAULT_DISRUPTOR_BUFFER_SIZE = "512";
    public static final String DEFAULT_DISRUPTOR_COUNT = "5";
    public static final String DEFAULT_DISRUPTOR_EVENT_HANDLER_COUNT = "1";
    public static final String DEFAULT_WAIT_STRATEGY = Constants.PHASED_BACKOFF;
    public static final String DEFAULT_SHARE_DISRUPTOR_WITH_OUTBOUND = "false";
    public static final String DEFAULT_DISRUPTOR_CONSUMER_EXTERNAL_WORKER_POOL = "0";
    public static final int DEFAULT_EXEC_HANDLER_THREAD_POOL_SIZE = 60;

    public static final String DISRUPTOR_CONSUMER_EXTERNAL_WORKER_POOL = "disruptor.consumer.worker.pool.size";

}
