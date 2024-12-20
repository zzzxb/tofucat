package xyz.zzzxb.tofucat.common.annotation;

import java.lang.annotation.*;

/**
 * @author zzzxb
 * 2024/12/17
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HiddenType {

    String value() default "hidden content";
}
