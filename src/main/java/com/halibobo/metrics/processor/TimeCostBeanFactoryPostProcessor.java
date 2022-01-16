package com.halibobo.metrics.processor;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Component("timeFactoryPost")
@Slf4j(topic = "processor")
public class TimeCostBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Getter
    private long launchTime;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        launchTime = System.currentTimeMillis();
        logger.info("begin launch:{}", launchTime);
    }
}