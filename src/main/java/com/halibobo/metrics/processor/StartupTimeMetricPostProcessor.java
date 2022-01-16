package com.halibobo.metrics.processor;

import com.google.common.collect.Maps;
import com.halibobo.metrics.bean.BeanStatistics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j(topic = "processor")
@Component("metricPostProcessor")
public class StartupTimeMetricPostProcessor implements InstantiationAwareBeanPostProcessor, PriorityOrdered, ApplicationRunner {

    private Map<String, BeanStatistics> statisticsMap = Maps.newTreeMap();
    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<BeanStatistics>  beanPostProcessStatisticsList= statisticsMap.values().stream()
                .sorted(Comparator.comparing(BeanStatistics::calculateTotalCostTime).reversed())
                .filter(m-> m.calculateTotalCostTime() > 0)
                .collect(Collectors.toList());

        StringBuilder statisticsLog =new StringBuilder();

        beanPostProcessStatisticsList.forEach(
                statisticsLog::append);
        logger.info("ApplicationStartupTimeMetric:\n{}", statisticsLog.toString());

    }

    @Override
    public int getOrder() {
        return PriorityOrdered.HIGHEST_PRECEDENCE;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        String beanClassName = beanClass.getName();
        BeanStatistics statistics =
                BeanStatistics.builder().beanName(beanClassName).build();
        statistics.setBeforeInstantiationTime(System.currentTimeMillis());
        statisticsMap.put(beanClassName, statistics);
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        String beanClassName = bean.getClass().getName();
        BeanStatistics statistics = statisticsMap.get(beanClassName);
        if (statistics != null) {
            statistics.setAfterInstantiationTime(System.currentTimeMillis());
        }
        return true;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        String beanClassName = bean.getClass().getName();
        BeanStatistics statistics = statisticsMap.getOrDefault(
                beanClassName, BeanStatistics.builder().beanName(beanClassName).build());
        statistics.setBeforeInitializationTime(System.currentTimeMillis());
        statisticsMap.putIfAbsent(beanClassName, statistics);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        String beanClassName = bean.getClass().getName();
        BeanStatistics statistics = statisticsMap.get(beanClassName);
        if (statistics != null) {
            statistics.setAfterInitializationTime(System.currentTimeMillis());
        }
        return bean;
    }
}
