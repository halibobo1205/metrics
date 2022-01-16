package com.halibobo.metrics.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j(topic = "service")
public class MService {

    @Autowired DB dB;
    @Autowired Node node;

    public void start() {
        logger.info("service start");
        node.init();
    }
}
