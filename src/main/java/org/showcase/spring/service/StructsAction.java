package org.showcase.spring.service;

import org.showcase.spring.core.annotation.Autowired;
import org.showcase.spring.core.annotation.Component;
import org.showcase.spring.core.annotation.Scope;
import org.showcase.spring.service.UserBean;

@Component
@Scope("prototype")
public class StructsAction {
    @Autowired
    private UserBean userBean;
    public void list(){
        System.out.println("list method invoke");
        userBean.list();
    }
}
