package xyz.zzzxb.tofucat.common.algorithm;

import xyz.zzzxb.tofucat.common.exception.ParamException;
import xyz.zzzxb.tofucat.common.utils.CheckParamUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptBase {
    private static final char[] HEX_CHARS = getHexChars();

    public String encrypt(String content, Encrypt encrypt) {
        return encrypt(content.getBytes(), encrypt);
    }

    public String encrypt(byte[] content, Encrypt encrypt) {
        return encode(buildDigest(content, encrypt));
    }

    public String encrypt(byte content, Encrypt encrypt) {
        return encode(buildDigest(content, null, encrypt));
    }

    public byte[] buildDigest(byte[] contentBytes, Encrypt encrypt) {
        return buildDigest((byte) 0, contentBytes, encrypt);
    }

    public byte[] buildDigest(byte contentByte, byte[] contentBytes, Encrypt encrypt) {
        CheckParamUtils.isNull(encrypt).throwMessage("encrypt algorithm is null");

        MessageDigest instance;
        try {
            instance = MessageDigest.getInstance(encrypt.toString());
        } catch (NoSuchAlgorithmException e) {
            throw new ParamException("not found " + encrypt.toString() + " algorithm");
        }
        if (contentBytes != null) {
            instance.update(contentBytes);
        } else {
            instance.update(contentByte);
        }
        return instance.digest();
    }

    private String encode(byte[] digest) {
        int digestLength = digest.length;
        char[] hexChar = new char[digestLength * 2];
        int hexCharIndex = 0;
        for (byte digestOne : digest) {
            hexChar[hexCharIndex++] = HEX_CHARS[digestOne >>> 4 & 0xf];
            hexChar[hexCharIndex++] = HEX_CHARS[digestOne & 0xf];
        }
        return new String(hexChar);
    }

    private static char[] getHexChars() {
        return new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    }

    public enum Encrypt {
        MD5("MD5"),
        SHA1("SHA-1"),
        SHA256("SHA-256");

        private final String encryptType;

        Encrypt(String encryptType) {
            this.encryptType = encryptType;
        }

        @Override
        public String toString() {
            return this.encryptType;
        }
    }
}
