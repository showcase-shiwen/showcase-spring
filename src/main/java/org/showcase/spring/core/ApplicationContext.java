package org.showcase.spring.core;

import org.showcase.spring.core.annotation.*;
import org.showcase.spring.core.bean.BeanNameAware;
import org.showcase.spring.core.bean.BeanPostProcessor;
import org.showcase.spring.core.bean.InitializingBean;
import org.showcase.spring.core.enums.BeanScope;
import org.showcase.spring.core.util.StringUtil;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 参照  https://blog.csdn.net/weixin_44235759/article/details/123634022
 */
public class ApplicationContext {
    private Class configClass;

    private ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    //单例池
    private ConcurrentHashMap<String, Object> singletonObjects = new ConcurrentHashMap<>();

    //所有类实例
    private ConcurrentHashMap<String, Object> instanceObjects = new ConcurrentHashMap<>();


    public ApplicationContext(Class configClass) {
        this.configClass = configClass;

        if (configClass.isAnnotationPresent(ComponentScan.class)) {
            ComponentScan componentScanAnnotation = (ComponentScan) configClass.getAnnotation(ComponentScan.class);
            String scanPackageName = componentScanAnnotation.value();//扫描路径
            if (StringUtil.isEmpty(scanPackageName)) {
                scanPackageName = configClass.getPackage().getName();
            }
            String path = scanPackageName.replace(".", "/");


            ClassLoader classLoader = ApplicationContext.class.getClassLoader();
            URL resource = classLoader.getResource(path);
            File file = new File(resource.getFile());

            scanClass(file, scanPackageName, classLoader);

            //扫描到的bean 实例化
            beanDefinitionMapInstance();


            //beanRegister
            beanRegister();


        }
    }

    private void beanRegister() {
        Set<String> keySet=beanDefinitionMap.keySet();
        keySet.forEach(beanName->{
            BeanDefinition beanDefinition=beanDefinitionMap.get(beanName);
            if(BeanScope.singleton.name().equals(beanDefinition.getScope())){
               singletonObjects.put(beanName,createBean(beanName,beanDefinition));
            }
        });
        System.out.println("register success");
    }

    private void beanDefinitionMapInstance() {
        //遍历出单例bean
        for (String beanName : beanDefinitionMap.keySet()) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if (BeanScope.singleton.name().equals(beanDefinition.getScope())) {
                //将单例bean放入到单例池中
                Object bean = createInstance(beanDefinition.getType());
                instanceObjects.put(beanName, bean);
            }
        }
    }

    private Object createBean(String beanName, BeanDefinition beanDefinition) {
        //bean 创建
        Class clazz = beanDefinition.getType();
        Object instance = createInstance(beanDefinition.getType());

        Field[] fields=clazz.getDeclaredFields();
        for(Field field:fields){
            try {
                if(field.isAnnotationPresent(Autowired.class)&&!field.isAnnotationPresent(Lazy.class)){
                    field.setAccessible(true);
                    Object bean=singletonObjects.get(field.getName());
                    if(bean==null){
                        bean=instanceObjects.get(field.getName());
                    }
                    field.set(instance,bean);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (instance instanceof BeanNameAware) {
            try {
                ((BeanNameAware) instance).setBeanName(beanName);
            } catch (Exception e) {
            }
        }
        //初始化
        if (instance instanceof InitializingBean) {
            try {
                ((InitializingBean) instance).afterPropertiesSet();
            } catch (Exception e) {
            }
        }

        return instance;
    }

    private Object createInstance( Class clazz) {
        try {
            Object instance = clazz.getConstructor().newInstance();
           return instance;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void scanClass(File file, String scanPackageName, ClassLoader classLoader) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                if (f.isDirectory()) {
                    scanClass(f, scanPackageName, classLoader);
                } else {
                    String fileName = f.getAbsolutePath();
                    if (fileName.endsWith(".class")) {
                        try {
                            String className = fileName.substring(fileName.indexOf(scanPackageName.split("\\.")[0]), fileName.indexOf(".class"));
                            className = className.replace("\\", ".");
                            Class<?> clazz = classLoader.loadClass(className);

                            if (clazz.isAnnotationPresent(Component.class)) {

                                Component component = clazz.getAnnotation(Component.class);
                                String beanName = component.value();
                                if (StringUtil.isEmpty(beanName)) {
                                    beanName = StringUtil.lowerCaseFirst(clazz.getSimpleName());
                                }
                                BeanDefinition beanDefinition = new BeanDefinition();
                                beanDefinition.setType(clazz);
                                if (clazz.isAnnotationPresent(Scope.class)) {
                                    Scope scopeAnnotation = clazz.getAnnotation(Scope.class);
                                    beanDefinition.setScope(scopeAnnotation.value());
                                } else {
                                    beanDefinition.setScope(BeanScope.singleton.name());
                                }
                                beanDefinitionMap.put(beanName, beanDefinition);
                            }
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }

                    }
                }

            }
        }

    }


    public Object getBean(String beanName) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null) {
            throw new NullPointerException();
        } else {
            String scope = beanDefinition.getScope();
            if (BeanScope.singleton.name().equals(scope)) {
                //单例
                Object bean = singletonObjects.get(beanName);


                if (bean == null) {
                    bean= createBean(beanName, beanDefinition);
                    singletonObjects.put(beanName,bean);
                }
                //懒加载逻辑
//                boolean lazy=false;
//
//                Field[] fields=beanDefinition.getType().getDeclaredFields();
//                for(Field field:fields){
//                    if(field.isAnnotationPresent(Lazy.class)){
//                        lazy=true;
//                        field.setAccessible(true);
//                        try {
//                            field.set(bean,getBean(field.getName()));
//                        } catch (IllegalAccessException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//                if(lazy){
//                    singletonObjects.put(beanName,bean);
//                }

                return bean;
            } else {
                //多例
                return createBean(beanName, beanDefinition);
            }
        }
    }
}
