package com.halibobo.metrics.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j(topic = "cache")
public class Cache {

    private String name;

    @Getter
    private long initTime;

    public Cache(String name, long delay) {
        this.name = name;
        init(delay);
    }

    private void init(long delay) {
        long s = System.currentTimeMillis();
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            logger.error("error:" ,e);
        }
        this.initTime = System.currentTimeMillis() - s;
        logger.info("{} initTime {} ms", name,initTime);
    }
}
