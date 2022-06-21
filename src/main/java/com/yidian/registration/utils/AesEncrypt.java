package com.yidian.registration.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * 本系统核心秘钥服务类，用于对保存入库数据的加解密处理
 * 
 */
public class AesEncrypt {
	public static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(AesEncrypt.class);

	/** 加密私钥的版本号，用于识别秘钥的标志 */
	public static final int ENCRYPT_VERSION = 1;
	/** 加密算法 */
	public static final String ALGORITHM_AES = "AES";

	/** AES对称加密算法的秘钥 */
	private SecretKeySpec privateKey = null;

	private static final String UTF_8 = "UTF-8";

	public AesEncrypt() {
	}//constructor

	public AesEncrypt(String aesEncryptKey) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance(ALGORITHM_AES);// 创建AES的Key生产者

			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");//必须使用此实例化方法，否则在linux上每次都产生不同的秘钥
			secureRandom.setSeed(aesEncryptKey.getBytes());
			kgen.init(128, secureRandom);// 利用用户密码作为随机数初始化出
			SecretKey secretKey = kgen.generateKey();// 根据用户密码，生成一个密钥

			byte[] encodeBytes = secretKey.getEncoded();// 返回基本编码格式的密钥，如果此密钥不支持编码，则返回
			privateKey = new SecretKeySpec(encodeBytes, ALGORITHM_AES);// 转换为AES专用密钥
			LOG.info("AES私钥长度：{},{}~{}", encodeBytes.length, encodeBytes[0], encodeBytes[encodeBytes.length - 1]);

		} catch (Exception e) {
			LOG.error("【初始化失败{}】{}", this, e.getMessage());
			throw new RuntimeException(e);
		}
	}//constructor

	public void setSecretKey(String base64) {
		byte[] encodeBytes = Base64Util.decode(base64);
		privateKey = new SecretKeySpec(encodeBytes, ALGORITHM_AES);// 转换为AES专用密钥
		LOG.info("AES私钥长度：{},{}~{}", encodeBytes.length, encodeBytes[0], encodeBytes[encodeBytes.length - 1]);
	}//method

	/** 加密 */
	public byte[] encrypt(byte[] plain) {
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM_AES);// 创建密码器
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);// 初始化为加密模式的密码器
			byte[] result = cipher.doFinal(plain);// 加密
			return result;
		} catch (Exception e) {
			LOG.error("【加密错误】{}", e.getMessage());
			throw new RuntimeException(e);
		}
	}//method

	/** 解密 */
	public byte[] decrypt(byte[] encrypt) {
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM_AES);// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, privateKey);// 初始化为解密模式的密码器
			byte[] result = cipher.doFinal(encrypt);
			return result;
		} catch (Exception e) {
			LOG.error("【解密错误】{}", e.getMessage());
			throw new RuntimeException(e);
		}
	}//method

	/**
	 * 返回base64编码后的字符串
	 */
	public String encrypt(String plain) {
		try {
			byte[] plainBytes = plain.getBytes(UTF_8);
			byte[] encryptBytes = encrypt(plainBytes);
			String result = Base64Util.encodeToString(encryptBytes);
			return result;
		} catch (Exception e) {
			LOG.error("【加密错误】{}", e.getMessage());
			throw new RuntimeException(e);
		}
	}//method

	/**
	 * 返回明文字符串，UTF8编码
	 * @param encrypt base64编码的密文
	 */
	public String decrypt(String encrypt) {
		try {
			byte[] encryptBytes = Base64Util.decode(encrypt);
			byte[] plainBytes = decrypt(encryptBytes);
			return new String(plainBytes, UTF_8);
		} catch (Exception e) {
			LOG.error("【解密错误】{}", e.getMessage());
			throw new RuntimeException("参数解密失败,"+e.getMessage());
		}
	}//method

	/**
	 * 根据 AES算法，随机生成私钥；
	 * @return  用BASE64编码返回私钥
	 */
	public static String createAESSecureRandomKey() {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance(ALGORITHM_AES);// 创建AES的Key生产者
			SecureRandom secureRandom = new SecureRandom();
			kgen.init(128, secureRandom);// 利用用户密码作为随机数初始化出; 支持128,192,256位
			SecretKey secretKey = kgen.generateKey();// 根据用户密码，生成一个密钥

			byte[] encodeBytes = secretKey.getEncoded();// 返回基本编码格式的密钥，如果此密钥不支持编码，则返回

			String key = Base64Util.encodeToString(encodeBytes);
			return key;

		} catch (Exception e) {
			String err = "生成随机安全私钥错误：" + e.getMessage();
			LOG.error(err, e);
			throw new RuntimeException(err);
		}
	}//method

}//class
