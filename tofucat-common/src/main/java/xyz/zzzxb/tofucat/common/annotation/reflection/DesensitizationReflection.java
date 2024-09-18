package xyz.zzzxb.tofucat.common.annotation.reflection;

import xyz.zzzxb.tofucat.common.annotation.Desensitization;
import xyz.zzzxb.tofucat.common.utils.StringUtils;
import xyz.zzzxb.tofucat.common.utils.Ten2Thirty;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * zzzxb
 * 2024/4/7
 */
public class DesensitizationReflection {

    public void handle(Object obj) {
        if (obj == null) { return;}

        Class<?> aClass = obj.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        Field[] superFields = aClass.getSuperclass().getDeclaredFields();

        List<Field> fieldList = new LinkedList<>();
        Collections.addAll(fieldList, declaredFields);
        Collections.addAll(fieldList, superFields);

        for (Field declaredField : fieldList) {
            Annotation[] declaredAnnotations = declaredField.getDeclaredAnnotations();
            for (Annotation declaredAnnotation : declaredAnnotations) {
                if (declaredAnnotation instanceof Desensitization) {
                    try {
                        String getMethodName = "get" + StringUtils.firstUpper(declaredField.getName());
                        Method getMethod = aClass.getMethod(getMethodName);
                        String value = (String) getMethod.invoke(obj);

                        String setMethodName = "set" + StringUtils.firstUpper(declaredField.getName());
                        Method setMethod = aClass.getMethod(setMethodName, declaredField.getType());
                        if(Pattern.matches(((Desensitization) declaredAnnotation).regex(), value)) {
                            setMethod.invoke(obj, Ten2Thirty.decimalToBase32(Long.parseLong(value)));
                        }else {
                            setMethod.invoke(obj, String.valueOf(Ten2Thirty.base32ToDecimal(value)));
                        }
                    }catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
