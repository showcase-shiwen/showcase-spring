package org.showcase.spring;

import org.showcase.spring.core.ApplicationContext;
import org.showcase.spring.core.annotation.ComponentScan;
import org.showcase.spring.service.UserBean;

@ComponentScan
public class Application {
    public static void main(String[] args){
        ApplicationContext applicationContext=new ApplicationContext(Application.class);
        System.out.println( applicationContext.getBean("userBean"));
        System.out.println( applicationContext.getBean("userBean"));
        System.out.println( applicationContext.getBean("structsAction"));
        System.out.println( applicationContext.getBean("structsAction"));
        UserBean userBean= (UserBean) applicationContext.getBean("userBean");
        userBean.hello();
    }
}
