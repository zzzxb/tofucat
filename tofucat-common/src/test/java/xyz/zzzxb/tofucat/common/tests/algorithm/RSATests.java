package xyz.zzzxb.tofucat.common.tests.algorithm;

import org.junit.jupiter.api.Test;
import xyz.zzzxb.tofucat.common.algorithm.RSA;

import java.io.File;
import java.security.NoSuchAlgorithmException;

/**
 * @author zzzxb
 * 2024/9/18
 */
public class RSATests {

    @Test
    public void buildKey() throws NoSuchAlgorithmException {
        RSA.getInstance().writeKey(2048, "../etc");
    }

    @Test
    public void encode() {
        String encode = RSA.getInstance().encode(new File("../etc/pub.key"), RSA.Key.PublicKey, "umcc#8");
        String decode = RSA.getInstance().decode(new File("../etc/pri.key"), RSA.Key.PrivateKey, encode);
    }
}
