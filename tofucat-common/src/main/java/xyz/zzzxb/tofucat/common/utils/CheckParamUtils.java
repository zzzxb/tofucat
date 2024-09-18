package xyz.zzzxb.tofucat.common.utils;


import xyz.zzzxb.tofucat.common.exception.ParamException;
import xyz.zzzxb.tofucat.common.exception.ThrowExceptionFun;

import java.util.Collection;
import java.util.regex.Pattern;

/**
 * zzzxb
 * 2023/8/10
 */
public final class CheckParamUtils {

    public static ThrowExceptionFun isBlack(String s) {
        return isTrue(StringUtils.isBlank(s));
    }

    public static ThrowExceptionFun equals(String a, String b) {
        return isTrue(a.equals(b));
    }

    public static ThrowExceptionFun noEquals(String a, String b) {
        return isTrue(!a.equals(b));
    }

    public static ThrowExceptionFun isNull(Object o) {
        return isTrue(o == null);
    }

    public static ThrowExceptionFun notMatchRegex(String regex, String s) {
        return isFalse(Pattern.matches(regex, s));
    }

    public static ThrowExceptionFun collectionIsEmpty(Collection<?> collection) {
        return isTrue(collection.isEmpty());
    }

    public static ThrowExceptionFun isFalse(boolean bool) {
        return isTrue(!bool);
    }

    public static ThrowExceptionFun isTrue(boolean bool) {
        return (errorMsg, args) -> {
            if (bool) throw new ParamException(StringUtils.format(errorMsg, args));
        };
    }
}
