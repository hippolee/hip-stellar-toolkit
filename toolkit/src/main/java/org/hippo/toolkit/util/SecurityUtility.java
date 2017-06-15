package org.hippo.toolkit.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密/解密工具类
 * <p>
 * Created by litengfei on 2017/5/26.
 */
public class SecurityUtility {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(SecurityUtility.class);

    /**
     * Base64加密
     *
     * @param bytes
     * @return
     */
    public static String encryptBASE64(byte[] bytes) {
        byte[] encryptBytes = Base64.encodeBase64(bytes);
        return new String(encryptBytes);
    }

    /**
     * BASE64加密
     *
     * @param originalStr
     * @return
     */
    public static String encryptBASE64(String originalStr) {
        return encryptBASE64(originalStr.getBytes());
    }

    /**
     * Base64解密
     *
     * @param cipherStr
     * @return
     */
    public static String decryptBASE64(String cipherStr) {
        byte[] decryptBytes = Base64.decodeBase64(cipherStr);
        return new String(decryptBytes);
    }

    /**
     * MD5加密
     *
     * @param originalStr
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String encryptMD5(String originalStr) throws NoSuchAlgorithmException {
        // digest
        MessageDigest digest = MessageDigest.getInstance("MD5");
        // 计算md5函数
        digest.update(originalStr.getBytes());
        return new String(Hex.encodeHex(digest.digest(), false));
    }

}
