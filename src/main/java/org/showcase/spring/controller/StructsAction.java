package org.showcase.spring.controller;

import org.showcase.spring.core.annotation.Component;
import org.showcase.spring.core.annotation.Scope;

@Component
@Scope("prototype")
public class StructsAction {
    public void list(){
        System.out.println("list method invoke");
    }
}
