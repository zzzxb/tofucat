package xyz.zzzxb.tofucat.common.utils;

import java.util.Random;

/**
 * @author zzzxb
 * 2024/11/7
 */
public class MathUtils {
    private static final Random random = new Random();

    public static int randomInt(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public static long randomLong(long min, long max) {
        int defaultIntMax = 0x77359400;
        if (max < defaultIntMax) {
            return randomInt((int) min, (int) max);
        }

        int group = (int) (max / defaultIntMax);
        int remainder = (int) (max % defaultIntMax);
        long count = min;
        for (int i = 0; i < group; i++) {
            count += randomInt(0, defaultIntMax);
        }
        if (remainder != 0) {
            count += randomInt(0, remainder);
        }
        return count;
    }
}
