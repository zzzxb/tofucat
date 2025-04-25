package cn.tofucat.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * zzzxb
 * 2024/4/7
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Desensitization {
    boolean in() default true;
    boolean out() default true;
    String regex() default "^[0-9]{11}";
}
