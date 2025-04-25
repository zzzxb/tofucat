package cn.tofucat.common.utils;

/**
 * @author zzzxb
 * 2024/11/7
 */
public class RandomUtils {

    public static String random09az(int num) {
        num = num <= 0 ? 1 : num;
        int max = 9 + 26;
        int min = 0;
        char[] chars = new char[num];
        for (int i = 0; i < num; i++) {
            int r = MathUtils.randomInt(min, max);
            chars[i] = (char)((r < 10 ? '0' : 88) + r);
        }
        return String.valueOf(chars);
    }
}
