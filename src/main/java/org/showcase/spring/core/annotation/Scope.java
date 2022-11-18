package org.showcase.spring.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) //生效时间
@Target(ElementType.TYPE) //只能写在类上面
public @interface Scope {
    String value() default "";
}
