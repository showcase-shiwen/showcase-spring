package org.showcase.spring.service;

import org.showcase.spring.core.annotation.Autowired;
import org.showcase.spring.core.annotation.Component;
import org.showcase.spring.core.annotation.Lazy;

import javax.annotation.Resource;

@Component
public class BeanC {
    @Autowired
    @Lazy
    private BeanA beanA;

    public BeanA getBeanA() {
        return beanA;
    }

    public void setBeanA(BeanA beanA) {
        this.beanA = beanA;
    }
}
