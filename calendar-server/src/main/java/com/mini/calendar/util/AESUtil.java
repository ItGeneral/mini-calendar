package com.mini.calendar.util;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Security;

/**
 * @author songjiuhua
 * Created by 2020/8/17 10:48
 */
public class AESUtil {

    private static final String AES_KEY = "JaEtAXG8Mzzz5HEE";

    private static final String CIPHER_MODE = "AES/CBC/PKCS7Padding";

    private static final String ENCRYPT_ALG = "AES";

    public static String aesEncode(String content) {
        try {
            Security.addProvider(new BouncyCastleProvider());
            byte[] keyBytes = AES_KEY.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec key = new SecretKeySpec(keyBytes, ENCRYPT_ALG);

            Cipher cipher = Cipher.getInstance(CIPHER_MODE);
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(keyBytes));

            byte[] data = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
            return Base64.encodeBase64String(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String alsrobotwrite = AESUtil.aesEncode("als-robot-db-common-server.cluster-custom-cznqgcwo1pjt.us-east-1.rds.amazonaws.com");
        System.out.println(alsrobotwrite);
        String s = AESUtil.aesDecrypt(alsrobotwrite);
        System.out.println(s);
    }

    public static String aesDecrypt(String context) {
        try {
            Security.addProvider(new BouncyCastleProvider());
            byte[] keyBytes = AES_KEY.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec key = new SecretKeySpec(keyBytes, ENCRYPT_ALG);
            Cipher cipher = Cipher.getInstance(CIPHER_MODE);
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(keyBytes));

            byte[] content = cipher.doFinal(Base64.decodeBase64(context));
            return new String(content,StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
