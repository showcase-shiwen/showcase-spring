package org.showcase.spring.service;

import org.showcase.spring.core.annotation.Autowired;
import org.showcase.spring.core.annotation.Lazy;
import org.showcase.spring.core.bean.InitializingBean;
import org.showcase.spring.core.bean.BeanNameAware;
import org.showcase.spring.core.annotation.Component;

@Component
public class UserBean implements BeanNameAware, InitializingBean {
    private String beanName;

    @Autowired
    @Lazy
    private RoleBean roleBean;

    public void list() {

        System.out.println("user list method invoke");
        roleBean.list();
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("afterPropertiesSet  beanName:" + beanName);
    }

    public void callback() {
        System.out.println("callback");
    }
}
