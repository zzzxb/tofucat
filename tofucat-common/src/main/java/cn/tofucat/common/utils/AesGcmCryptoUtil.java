package cn.tofucat.common.utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AesGcmCryptoUtil {

	private static final Logger log = LoggerFactory.getLogger(AesGcmCryptoUtil.class);

	/**
	 * 默认KEY填充算法
	 */
	private static final String KEY_ALGORITHM = "AES";

	private static String gcm256algorithm = "AES/GCM/PKCS5Padding";

	/**
	 * 解密
	 *
	 * @param content 待解密文本
	 * @param keyStr  秘钥
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String content, String keyStr) throws Exception {
		try {
			if (StringUtils.isBlank(content) || StringUtils.isBlank(keyStr)) {
				throw new Exception("AESGCM256解密异常,检查文本或密钥");
			}
			Cipher cipher = Cipher.getInstance(gcm256algorithm);
			SecretKey key = getSecretKey(keyStr);

			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] message = Base64.getDecoder().decode(content);
			// 这里的12和16是加密的时候设置的偏移参数及加密长度
			if (message.length < 12 + 16) {
				throw new IllegalArgumentException();
			}
			GCMParameterSpec params = new GCMParameterSpec(128, message, 0, 12);
			cipher.init(Cipher.DECRYPT_MODE, key, params);
			byte[] decryptData = cipher.doFinal(message, 12, message.length - 12);
			return new String(decryptData,"utf-8");
		} catch (Exception e) {
			log.error("AES GCM 128解密文本处理失败,error:", e);
			throw new Exception(e);
		}
	}

	/**
	 * 加密
	 *
	 * @param content 待解密文本
	 * @param keyStr  秘钥
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String content, String keyStr) throws Exception {
		try {
			if (StringUtils.isBlank(content) || StringUtils.isBlank(keyStr)) {
				throw new Exception("AESGCM256加密异常,检查文本或密钥");
			}
			SecretKey secretKey = getSecretKey(keyStr);

			Cipher cipher = Cipher.getInstance(gcm256algorithm);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] iv = cipher.getIV();
			// 偏移参数及长度要在解密的时候保持一致
			assert iv.length == 12;
			byte[] byteContent = content.getBytes("utf-8");
			byte[] encryptData = cipher.doFinal(byteContent);
			assert encryptData.length == byteContent.length + 16;
			byte[] messageByte = new byte[12 + byteContent.length + 16];
			System.arraycopy(iv, 0, messageByte, 0, 12);
			System.arraycopy(encryptData, 0, messageByte, 12, encryptData.length);
			return Base64.getEncoder().encodeToString(messageByte);
		} catch (Exception e) {
			log.error("AES GCM 128加密文本处理失败,error:", e);
			throw new Exception(e);
		}
	}

	/**
	 * 生成加密秘钥，AES 128对应的秘钥长度是16字节
	 *
	 * @return 返回加密秘钥
	 */
	private static SecretKeySpec getSecretKey(final String password) throws Exception {
		String tempKey = password.substring(0, 16);
		// 转换为AES专用密钥
		return new SecretKeySpec(tempKey.getBytes(), KEY_ALGORITHM);
	}

}
