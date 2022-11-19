package org.showcase.spring.service;

import org.showcase.spring.core.annotation.Component;

import javax.annotation.Resource;

@Component
public class BeanB {
    @Resource
    private BeanC beanC;

    public BeanC getBeanC() {
        return beanC;
    }

    public void setBeanC(BeanC beanC) {
        this.beanC = beanC;
    }
}
