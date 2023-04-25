package org.showcase.spring;

import org.showcase.spring.core.ApplicationContext;
import org.showcase.spring.core.annotation.ComponentScan;
import org.showcase.spring.service.BeanA;
import org.showcase.spring.service.StructsAction;

@ComponentScan
public class Application {
    public static void main(String[] args){
        ApplicationContext applicationContext=new ApplicationContext(Application.class);
        System.out.println( applicationContext.getBean("userBean"));
        System.out.println( applicationContext.getBean("userBean"));
        //多实例无法注入
        System.out.println(applicationContext.getBean("structsAction"));
        StructsAction structsAction= (StructsAction) applicationContext.getBean("structsAction");
        structsAction.list();
//
        BeanA beanA= (BeanA) applicationContext.getBean("beanA");
        System.out.println(beanA);
        System.out.println(beanA.getBeanB());
        System.out.println(beanA.getBeanB().getBeanC());
        System.out.println(beanA.getBeanB().getBeanC().getBeanA().getBeanB());
    }
}
