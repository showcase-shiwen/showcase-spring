package org.showcase.spring.service;

import org.showcase.spring.core.annotation.Autowired;
import org.showcase.spring.core.annotation.Component;
import org.showcase.spring.core.annotation.Lazy;

@Component
public class RoleBean {
    @Autowired
    @Lazy
    private UserBean userBean;

    public void list() {
        System.out.println("role  method invoke");
        userBean.callback();
    }
}
