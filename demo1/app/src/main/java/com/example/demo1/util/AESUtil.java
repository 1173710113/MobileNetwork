package com.example.demo1.util;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
 
/**
 * AES 加密方法，是对称的密码算法(加密与解密的密钥一致)，这里使用最大的 256 位的密钥
 */
public class AESUtil {
	
	private static final SecretKey secretKey = AESUtil.strKey2SecretKey("ZhRp7ulHChKT1AvX6oZfqYZNxwag6bEaLZBBPr/kgTg="); 
	
    /**
     * 获得一个 密钥长度为 256 位的 AES 密钥，
     * @return 返回经 BASE64 处理之后的密钥字符串
     */
    private static String getStrKeyAES() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = new SecureRandom(String.valueOf(System.currentTimeMillis()).getBytes("utf-8"));
        keyGen.init(256, secureRandom);   // 这里可以是 128、192、256、越大越安全
        SecretKey secretKey = keyGen.generateKey();
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }
 
    /**
     *  将使用 Base64 加密后的字符串类型的 secretKey 转为 SecretKey
     * @param strKey
     * @return SecretKey
     */
    private static SecretKey strKey2SecretKey(String strKey){
        byte[] bytes = Base64.getDecoder().decode(strKey);
        SecretKeySpec secretKey = new SecretKeySpec(bytes, "AES");
        return secretKey;
    }
 
    /**
     * 加密
     * @param content 待加密内容
     * @return 加密后的密文 
     */
    public static String encryptAES(String content) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        byte[] contentBytes = content.getBytes("utf-8");
    	Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptAESbytes = cipher.doFinal(contentBytes);
        String ciperStr = Base64.getEncoder().encodeToString(encryptAESbytes);
        return ciperStr;
    }
 
    /**
     * 解密
     * @param content 待解密内容
     * @return 解密后的明文
     */
    public static String decryptAES(String content) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
    	byte[] contentBytes = Base64.getDecoder().decode(content);
    	Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return new String(cipher.doFinal(contentBytes),"utf-8");
        		
    }
}