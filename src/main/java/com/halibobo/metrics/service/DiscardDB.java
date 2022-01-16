package com.halibobo.metrics.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j(topic = "db")
public class DiscardDB {

    @Getter
    private long initTime;

    @Autowired
    public DiscardDB(@Value("discard-cache") String dbName) {
        this.initTime = new Cache(dbName,20_000).getInitTime();
    }
}
