package org.hippo.toolkit.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Calendar;

/**
 * 加密/解密工具类
 *
 * Created by litengfei on 2017/5/26.
 */
public class HIPSecurityUtility {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(HIPSecurityUtility.class);

    /**
     * BASE64解密
     *
     * @param cipherStr
     * @return
     * @throws IOException
     */
    public static String decryptBASE64(String cipherStr) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        return bytesToHexString(decoder.decodeBuffer(cipherStr));
    }

    public static String encryptBASE642(byte[] bytes) {
        byte[] encodeBase64 = org.apache.commons.codec.binary.Base64
                .encodeBase64(bytes);
        System.out.println();
        return new String(encodeBase64);
    }

    /**
     * BASE64加密
     *
     * @param bytes
     * @return
     */
    public static String encryptBASE64(byte[] bytes) {
        return (new BASE64Encoder()).encodeBuffer(bytes);
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
        return bytesToHexString(digest.digest());
    }

    public static String[] generateKeyPairRSA() throws NoSuchAlgorithmException {
        // 初始化KeyPairGenerator
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(512);

        // 获得KeyPair
        KeyPair keyPair = keyPairGen.generateKeyPair();

        // 获得RSA公私钥对象RSAPublicKey RSAPrivateKey
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        // 获得公私钥字符串
        String publicKeyStr = bytesToHexString(publicKey.getEncoded());
        String privateKeyStr = bytesToHexString(privateKey.getEncoded());

        logger.info("RSA Public Key: {}", publicKeyStr);
        logger.info("RSA Private Key: {}", privateKeyStr);
        logger.info("RSA Public Key: {}", encryptBASE64(publicKey.getEncoded()));
        logger.info("RSA Private Key: {}", encryptBASE64(privateKey.getEncoded()));
        return new String[]{publicKeyStr, privateKeyStr};
    }

    /**
     * 16进制字符串转换为byte[]
     *
     * @param hexString
     * @return
     */
    private static byte[] hexStringToBytes(String hexString) {
        if (StringUtils.isEmpty(hexString)) {
            return null;
        }

        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * byte[]转换成16进制字符串
     *
     * @param bytes
     * @return
     */
    private static String bytesToHexString(byte[] bytes) {
        char[] alpha = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        StringBuilder strBuilder = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            strBuilder.append(alpha[b >>> 4 & 0xf]).append(alpha[b & 0xf]);
        }
        return strBuilder.toString();
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static void main(String[] args) {
        try {
            // "进行Base64".getBytes()//6L+b6KGMQmFzZTY0
            byte[] bytes = "进行Base64".getBytes();
            long t1 = Calendar.getInstance().getTimeInMillis();
            String result1 = "";
            for (int i = 0; i < 10000; i++) {
                result1 = encryptBASE64(bytes);
            }
            long t2 = Calendar.getInstance().getTimeInMillis();
            logger.info("result1:{}|{}", result1, t2 - t1);
            String result2 = "";
            for (int i = 0; i < 10000; i++) {
                result2 = encryptBASE642(bytes);
            }
            long t3 = Calendar.getInstance().getTimeInMillis();
            logger.info("result2:{}|{}", result2, t3 - t2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
