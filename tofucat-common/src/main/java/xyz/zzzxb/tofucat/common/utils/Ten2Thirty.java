package xyz.zzzxb.tofucat.common.utils;

public class Ten2Thirty {

    private static final char[] DIGITS = "0123456789ABCDEFGHIJKLMNOPQRSTUV".toCharArray();
    private static final int BASE = 32;

    // 将32进制字符串转换为10进制数
    public static long base32ToDecimal(String base32Str) {
        if (base32Str == null) {
            throw new IllegalArgumentException("The input string cannot be null.");
        }

        long result = 0;
        long power = 1;
        for (int i = base32Str.length() - 1; i >= 0; i--) {
            char c = base32Str.charAt(i);
            int digit = charToInt(c);
            result += digit * power;
            power *= BASE;
        }
        return result;
    }

    private static int charToInt(char c) {
        for (int i = 0; i < DIGITS.length; i++) {
            if (DIGITS[i] == c) {
                return i;
            }
        }
        throw new IllegalArgumentException("Invalid character found: " + c);
    }

    // 将10进制数转换为32进制字符串
    public static String decimalToBase32(long decimal) {
        if (decimal < 0) {
            throw new IllegalArgumentException("The decimal number must be non-negative.");
        }

        StringBuilder builder = new StringBuilder();
        do {
            int digitValue = (int)(decimal % BASE);
            builder.insert(0, DIGITS[digitValue]);
            decimal /= BASE;
        } while (decimal > 0);

        return builder.toString();
    }
}