package org.showcase.spring.service;

import org.showcase.spring.core.annotation.Autowired;
import org.showcase.spring.core.annotation.Component;
import org.showcase.spring.core.annotation.Lazy;

import javax.annotation.Resource;

@Component
public class BeanA {
    @Autowired
    @Lazy
    private BeanB beanB;

    public BeanB getBeanB() {
        return beanB;
    }

    public void setBeanB(BeanB beanB) {
        this.beanB = beanB;
    }
}
