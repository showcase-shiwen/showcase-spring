package org.showcase.spring.core.bean;

public interface BeanPostProcessor {
    void postProcessBeforeInitialization(String beanName,Object bean);
    void postProcessAfterInitialization(String beanName,Object bean);
}
