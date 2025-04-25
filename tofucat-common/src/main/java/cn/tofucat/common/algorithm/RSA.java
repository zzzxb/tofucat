package cn.tofucat.common.algorithm;


import cn.tofucat.common.utils.FileUtils;

import javax.crypto.Cipher;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * zzzxb
 * 2023/12/11
 */
public class RSA {
    private final static RSA INSTANCE = new RSA();
    public final static String ALGORITHM = "RSA";
    public final static Integer KEY_SIZE = 2048;


    private RSA() {}

    public static RSA getInstance() {
        return INSTANCE;
    }

    public KeyPair generatorKey(int len) throws NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance(ALGORITHM);
        generator.initialize(len < 1024 ? KEY_SIZE : len);
        return generator.generateKeyPair();
    }

    public void writeKey(int len, String outPath) throws NoSuchAlgorithmException {
        KeyPair keyPair = generatorKey(len);

        PublicKey pubKey = keyPair.getPublic();
        String pubKeyBase64 = Base64.getEncoder().encodeToString(pubKey.getEncoded());
        String pubKeyFormat = format("-----BEGIN PUBLIC KEY-----", "-----END PUBLIC KEY-----", pubKeyBase64, false);
        FileUtils.write(pubKeyFormat.getBytes(StandardCharsets.UTF_8), outPath + File.separator + "pub.key");

        PrivateKey priKey = keyPair.getPrivate();
        String priKeyBase64 = Base64.getEncoder().encodeToString(priKey.getEncoded());
        String priKeyFormat = format("-----BEGIN RSA PRIVATE KEY-----", "-----END RSA PRIVATE KEY-----", priKeyBase64 , false);
        FileUtils.write(priKeyFormat.getBytes(StandardCharsets.UTF_8), outPath + File.separator + "pri.key");
    }

    public String format(String prefix, String suffix, String content, boolean format) {
        if(format) {
            StringBuilder sb = new StringBuilder(prefix).append("\n");
            int group = (content.length() / 65) + ((content.length() % 65) > 0 ? 1 : 0);
            for (int i = 0; i < group; i++) {
                String substring = content.substring(i * 64, Math.min((i + 1) * 64, content.length()));
                sb.append(substring).append("\n");
            }
            sb.append(suffix);
            return sb.toString();
        }else {
            return content;
        }
    }


    public RsaKey readKey(File file, Key keyType) {
        String keyBase64 = new String(FileUtils.read(file), StandardCharsets.UTF_8);
        return readKey(keyBase64, keyType);
    }


    public RsaKey readKey(String key, Key keyType) {
        String base64Key = key.replace("-----BEGIN RSA PRIVATE KEY-----", "")
                .replace("-----END RSA PRIVATE KEY-----", "")
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\n", "");
        byte[] bytes = Base64.getDecoder().decode(base64Key);

        try {
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            if (keyType == Key.PrivateKey) {
                PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
                return new RsaKey((RSAPrivateKey) keyFactory.generatePrivate(spec));
            }
            EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
            return new RsaKey((RSAPublicKey) keyFactory.generatePublic(spec));
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String encode(File keyPath, Key keyType, String content) {
        return encode(readKey(keyPath, keyType), keyType, content);
    }

    public String encode(String key, Key keyType, String content) {
        return encode(readKey(key, keyType), keyType, content);
    }

    private String encode(RsaKey rsaKey, Key keyType, String content) {
        try {
            Cipher rsa = Cipher.getInstance(ALGORITHM);
            rsa.init(Cipher.ENCRYPT_MODE, keyType == Key.PublicKey ? rsaKey.getRsaPublicKey() : rsaKey.getRsaPrivateKey());
            byte[] bytes = rsa.doFinal(content.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(bytes);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String decode(File keyPath, Key keyType, String content) {
        return decode(readKey(keyPath, keyType), keyType, content);
    }

    public String decode(String key, Key keyType, String content) {
        return decode(readKey(key, keyType), keyType, content);
    }

    private String decode(RsaKey rsaKey, Key keyType, String content) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keyType == Key.PublicKey ? rsaKey.getRsaPublicKey() : rsaKey.getRsaPrivateKey());
            byte[] bytes = cipher.doFinal(Base64.getDecoder().decode(content));
            return new String(bytes, StandardCharsets.UTF_8);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public enum Key { PrivateKey, PublicKey }

    public static class RsaKey {
        private RSAPrivateKey rsaPrivateKey;
        private RSAPublicKey rsaPublicKey;
        public RsaKey(RSAPrivateKey rsaPrivateKey) { this.rsaPrivateKey = rsaPrivateKey; }
        public RsaKey(RSAPublicKey rsaPublicKey) { this.rsaPublicKey = rsaPublicKey; }

        public RsaKey(RSAPrivateKey rsaPrivateKey, RSAPublicKey rsaPublicKey) {
            this.rsaPrivateKey = rsaPrivateKey;
            this.rsaPublicKey = rsaPublicKey;
        }

        public RSAPrivateKey getRsaPrivateKey() {
            return rsaPrivateKey;
        }

        public void setRsaPrivateKey(RSAPrivateKey rsaPrivateKey) {
            this.rsaPrivateKey = rsaPrivateKey;
        }

        public RSAPublicKey getRsaPublicKey() {
            return rsaPublicKey;
        }

        public void setRsaPublicKey(RSAPublicKey rsaPublicKey) {
            this.rsaPublicKey = rsaPublicKey;
        }
    }
}
