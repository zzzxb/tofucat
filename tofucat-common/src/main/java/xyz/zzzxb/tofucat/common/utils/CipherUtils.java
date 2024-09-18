package xyz.zzzxb.tofucat.common.utils;


import xyz.zzzxb.tofucat.common.algorithm.EncryptBase;

import java.nio.charset.StandardCharsets;

public final class CipherUtils {
    private final static EncryptBase encryptBase = new EncryptBase();

    public static String md5_16(String str) {
        return md5_32(str).substring(8, 24);
    }

    public static String md5_16(byte[] bytes) {
        return md5_32(bytes).substring(8, 24);
    }

    public static String md5_32(String str) {
        CheckParamUtils.isNull(str).throwMessage("str is null");
        return encryptBase.encrypt(str, EncryptBase.Encrypt.MD5);
    }

    public static String md5_32(byte[] bytes) {
        CheckParamUtils.isNull(bytes).throwMessage("bytes is null");
        return encryptBase.encrypt(bytes, EncryptBase.Encrypt.MD5);
    }


    public static String sha256(String str) {
        CheckParamUtils.isNull(str).throwMessage("str is null");
        return encryptBase.encrypt(str, EncryptBase.Encrypt.SHA256);
    }

    public static String sha256(byte[] bytes) {
        CheckParamUtils.isNull(bytes).throwMessage("bytes is null");
        return encryptBase.encrypt(bytes, EncryptBase.Encrypt.SHA256);
    }

    public static String sha1(String str) {
        CheckParamUtils.isNull(str).throwMessage("str is null");
        return encryptBase.encrypt(str, EncryptBase.Encrypt.SHA1);
    }

    public static String sha1(byte[] bytes) {
        CheckParamUtils.isNull(bytes).throwMessage("bytes is null");
        return encryptBase.encrypt(bytes, EncryptBase.Encrypt.SHA1);
    }

    public static byte[] buildDigest(String content, EncryptBase.Encrypt encrypt) {
        return encryptBase.buildDigest(content.getBytes(StandardCharsets.UTF_8), encrypt);
    }

    public static byte[] buildDigest(byte[] contentBytes, EncryptBase.Encrypt encrypt) {
        return encryptBase.buildDigest(contentBytes, encrypt);
    }
}