package com.halibobo.metrics.bean;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "metrics")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BeanStatistics {

    private long beforeInstantiationTime;

    private long afterInstantiationTime;

    private long beforeInitializationTime;

    private long afterInitializationTime;

    private String beanName;

    public long calculateTotalCostTime() {

        return calculateInstantiationCostTime() + calculateInitializationCostTime();

    }

    public long calculateInstantiationCostTime() {

        return afterInstantiationTime - beforeInstantiationTime;

    }

    public long calculateInitializationCostTime() {

        return afterInitializationTime - beforeInitializationTime;

    }

    @Override
    public String toString() {

        StringBuilder sb =new StringBuilder();

        long totalCostTime = calculateTotalCostTime();
        sb.append("\t")
                .append(getBeanName())
                .append("\t")
                .append(totalCostTime)
                .append("\t\n");
        return sb.toString();

    }
}
