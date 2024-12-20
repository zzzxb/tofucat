package xyz.zzzxb.tofucat.common.tests.annotation;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;
import xyz.zzzxb.tofucat.common.annotation.HiddenType;
import xyz.zzzxb.tofucat.common.utils.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author zzzxb
 * 2024/12/17
 */
public class HiddenTypeTests {

    @Test
    public void a() {
        Object o = (Object)new Person("Zzzxb", "hi".getBytes(StandardCharsets.UTF_8), 18);
        HiddenType[] annotations = o.getClass().getAnnotationsByType(HiddenType.class);
        System.out.println(annotations);
    }

    @Test
    public void test() throws Exception {
        Object o = (Object)new Person("Zzzxb", "hi".getBytes(StandardCharsets.UTF_8), 18);
        Class<?> clazz = o.getClass();
        List<Field> fieldList = new LinkedList<>();
        fieldList.addAll(Arrays.asList(clazz.getFields()));
        fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
        for (Field field : fieldList) {
            HiddenType[] annotations = field.getAnnotationsByType(HiddenType.class);
            if(annotations.length != 0) {
                HiddenType annotation = annotations[0];
                String sm = "set" + StringUtils.firstUpper(field.getName());
                String simpleName = field.getType().getSimpleName();
                Method method = clazz.getMethod(sm, field.getType());
                if(simpleName.equals("String")) {
                    method.invoke(o, annotation.value());
                }else if (simpleName.equals("byte[]")) {
                    method.invoke(o, (Object) annotation.value().getBytes(StandardCharsets.UTF_8));
                }
            }
        }
    }
}

@Data
@AllArgsConstructor
@HiddenType
class Person {
    @HiddenType
    private String name;
    @HiddenType
    private byte[] content;
    private int age;
}