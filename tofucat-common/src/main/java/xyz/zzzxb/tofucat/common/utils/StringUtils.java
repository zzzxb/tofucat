package xyz.zzzxb.tofucat.common.utils;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Zzzxb
 */
public class StringUtils {
    private static final Pattern pattern = Pattern.compile("\\{}");

    /**
     * verify null or empty
     * <pre>
     * "" -> true
     * " " -> true
     * "\t" -> true
     * null -> true
     * </pre>
     * @param str String
     * @return isBlack return true
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * The result was the opposite
     * @see xyz.zzzxb.tofucat.common.utils.StringUtils#isBlank(String)
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * null or empty -> spare or else str
     * <pre>
     * str = "" -> spare
     * str = " " -> spare
     * str = "\n" -> spare
     * str = "donkey" -> str
     * </pre>
     */
    public static String spare(String str, String spare) {
        return isBlank(str) ? spare : str;
    }

    public static String join(String ...str) {
        return join(null, str);
    }

    public static String join(String symbol, String ...str){
        if(str == null || str.length == 0) return "";
        StringBuilder sb = new StringBuilder();
        if(isBlank(symbol)) {
            Arrays.stream(str).forEach(sb::append);
        }else {
            for (int i = 0; i < str.length; i++) {
                sb.append(str[i]).append((i != str.length - 1) ? symbol : "");
            }
        }
        return sb.toString();
    }

    /**
     *  Replaces the placeholder in the content with the specified variable
     *  <blockquote><pre>
     *   String str = StringUtils.format("d{}n{}{}", "o", "k", "ey");
     *   str.equals("donkey");
     *  </pre></blockquote>
     */
    public static String format(String str, Object ... args) {
        if(isBlank(str) || !str.contains("{}")) {
            return str;
        }
        ArrayDeque<Object> deque = new ArrayDeque<>(Arrays.asList(args));
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            str = matcher.replaceFirst(spare(String.valueOf(deque.poll()), ""));
            matcher.reset(str);
        }
        return str;
    }

    public static String firstUpper(String str) {
        if(isNotBlank(str)) {
            char firstChar = str.charAt(0);
            String capitalizeFirstChar = Character.toString(firstChar).toUpperCase();
            String remainder = str.substring(1);
            return capitalizeFirstChar + remainder;
        }
        throw new RuntimeException("字符串不能为空");
    }
}
