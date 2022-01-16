package com.halibobo.metrics.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j(topic = "node")
@Component
public class Node {

    private ScheduledExecutorService executor= Executors.newSingleThreadScheduledExecutor();

    private  volatile boolean isFirst = true;

    private long s;

    @Autowired
    private DB dB;

    public Node() {
        logger.info("node init");
        s = System.currentTimeMillis();
    }

    public void init() {
        executor.scheduleWithFixedDelay(() -> {
            try {
                if (isFirst) {
                    isFirst = false;
                    logger.info("first hit, after {} ms from construct" , System.currentTimeMillis() - s);
                    System.exit(0);
                }
            } catch (Throwable t) {
                logger.error("Exception", t);
            }
        }, 10000 - dB.getInitTime(), 2000, TimeUnit.MILLISECONDS);
    }
}
