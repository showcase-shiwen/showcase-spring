package org.showcase.spring.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * getBean 时生效
 */
@Retention(RetentionPolicy.RUNTIME) //生效时间
@Target(ElementType.FIELD) //只能写在属性上
public @interface Lazy {
}
