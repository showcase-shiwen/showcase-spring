package org.showcase.spring.service;

import org.showcase.spring.core.annotation.Component;
import org.showcase.spring.core.annotation.Scope;

@Component
public class UserBean {
    public void hello() {
        System.out.printf("hello method invoke");
    }
}
