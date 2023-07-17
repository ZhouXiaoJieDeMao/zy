package com.bsoft.common.utils.sign;


import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.bsoft.common.utils.http.HttpsClient;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class RsaUtil {
    public static Logger log = LoggerFactory.getLogger(RsaUtil.class);
    /**
     * 数字签名，密钥算法
     */
    private static final String RSA_KEY_ALGORITHM = "RSA";
    /**
     * 数字签名签名/验证算法
     */
    private static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    /**
     * 一次解密的长度
     */
    private static final int MAX_DECRYPT_BLOCK = 128;
    /**
     * 一次加密的长度
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * 密钥转成byte[]
     *
     * @param key
     * @return
     */
    public static byte[] decodeBase64(String key) {
        return Base64.decodeBase64(key);
    }

    /**
     * 公钥加密
     *
     * @param data      加密前的字符串
     * @param publicKey 公钥
     * @return 加密后的字符串
     * @throws Exception
     */
    public static String encryptByPublicKey(String data, String publicKey) {
        try {
            byte[] pubKey = decodeBase64(publicKey);
            byte[] enSign = encryptByPublicKey(data.getBytes(), pubKey);
            return Base64.encodeBase64String(enSign);
        } catch (Exception e) {
            log.error("error", e);
            return "";
        }

    }

    /**
     * 公钥加密
     *
     * @param data   待加密的数据
     * @param pubKey 公钥
     * @return 加密后的数据
     * @throws Exception
     */
    private static byte[] encryptByPublicKey(byte[] data, byte[] pubKey) throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(pubKey);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(x509EncodedKeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return segmentEncryption(data, cipher, 1);
    }

    /**
     * 私钥解密
     *
     * @param data   待解密的数据
     * @param priKey 私钥
     * @return 解密后的数据
     * @throws Exception
     */
    private static byte[] decryptByPrivateKey(byte[] data, byte[] priKey) throws Exception {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(priKey);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return segmentEncryption(data, cipher, 2);
    }

    /**
     * 私钥解密
     *
     * @param data       解密前的字符串
     * @param privateKey 私钥
     * @return 解密后的字符串
     * @throws Exception
     */
    public static String decryptByPrivateKey(String data, String privateKey) {
        try {
            byte[] priKey = decodeBase64(privateKey);
            byte[] design = decryptByPrivateKey(Base64.decodeBase64(data), priKey);
            return new String(design);
        } catch (Exception e) {
            log.error("error", e);
            return "";
        }

    }

    /**
     * RSA签名
     *
     * @param data   待签名数据
     * @param priKey 私钥
     * @return 签名
     * @throws Exception
     */
    private static String sign(byte[] data, byte[] priKey) throws Exception {
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(priKey);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        // 生成私钥
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 实例化Signature
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        // 初始化Signature
        signature.initSign(privateKey);
        // 更新
        signature.update(data);
        return Base64.encodeBase64String(signature.sign());
    }

    /**
     * RSA签名
     *
     * @param data   待签名数据
     * @param priKey 私钥
     * @return 签名
     * @throws Exception
     */
    public static String sign(String data, String priKey) {
        try {
            return sign(data.getBytes(), decodeBase64(priKey));
        } catch (Exception e) {
            log.error("error:", e);
            return "";
        }

    }

    /**
     * RSA校验数字签名
     *
     * @param data   待校验数据
     * @param sign   数字签名
     * @param pubKey 公钥
     * @return boolean 校验成功返回true，失败返回false
     */
    private static boolean verify(byte[] data, byte[] sign, byte[] pubKey) throws Exception {
        // 实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        // 初始化公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(pubKey);
        // 产生公钥
        PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
        // 实例化Signature
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        // 初始化Signature
        signature.initVerify(publicKey);
        // 更新
        signature.update(data);
        // 验证
        return signature.verify(sign);
    }

    /**
     * RSA校验数字签名
     *
     * @param data   待校验数据
     * @param sign   数字签名
     * @param pubKey 公钥
     * @return boolean 校验成功返回true，失败返回false
     */
    public static boolean verify(String data, String sign, String pubKey) {
        try {
            return verify(data.getBytes(StandardCharsets.UTF_8), decodeBase64(sign), decodeBase64(pubKey));
        } catch (Exception e) {
            log.error("error:", e);
            return false;
        }
    }

    /**
     * 分段加解密
     *
     * @param encryptedData
     * @param cipher
     * @param type 1.加密 2.解密
     * @return
     */
    private static byte[] segmentEncryption(byte[] encryptedData, Cipher cipher, int type) {
        int block = type == 1 ? MAX_ENCRYPT_BLOCK : MAX_DECRYPT_BLOCK;
        byte[] encryptedDatas = new byte[0];
        try {
            int inputLen = encryptedData.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密  doFinal
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > block) {
                    cache = cipher.doFinal(encryptedData, offSet, block);
                } else {
                    cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * block;
            }
            encryptedDatas = out.toByteArray();
            //获取到报文的长度
            int length = encryptedDatas.length;
            log.info("报文长度是：" + length);
            out.close();
        } catch (Exception e) {
            log.error("加密异常...", e);
        }
        return encryptedDatas;
    }

    private static HashMap<String, String> getTheKeys() {
        HashMap<String, String> keyPairMap = new HashMap<String, String>();
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 密钥大小：1024 位
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        String publicKey = printBase64Binary(keyPair.getPublic().getEncoded());
        String privateKey = printBase64Binary(keyPair.getPrivate().getEncoded());
        keyPairMap.put("publicKey", publicKey);
        keyPairMap.put("privateKey", privateKey);
        return keyPairMap ;
    }

    /**
     * 字节数组转字符
     */
    public static String printBase64Binary(byte[] bytes) {
        return DatatypeConverter.printBase64Binary(bytes);
    }
    /**
     * 字符转字节数组
     */
    public static byte[] parseBase64Binary(String value) {
        return DatatypeConverter.parseBase64Binary(value);
    }
    public static void main(String[] args) throws Exception {
        String url = "https://laqcm.linan.gov.cn:8443/latalent/boot/out/queryCUsers";
        Map<String,String> map = new LinkedHashMap<>();
        map.put("merchant","MER_YLTJ");
        String dateTime = DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss");
        map.put("time", dateTime);
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnkQQsfYwel3F62RYYPxj4X+TH4N9UbC1ZRqUCMeE/92RZfg8wqtQkLJP16WuNeUuj+ixA6mAWQ8XO3W7O25zV0Q07RNoP1unhcOttGSrTsvItPyLwHvm/w5TCyVQ/IGr9veJAiG2Jlw6EaTeBOzul1orv9ODZvgOrgglDoOoLXwIDAQAB";
        String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKeRBCx9jB6XcXrZFhg/GPhf5Mfg31RsLVlGpQIx4T/3ZFl+DzCq1CQsk/Xpa415S6P6LEDqYBZDxc7dbs7bnNXRDTtE2g/W6eFw620ZKtOy8i0/IvAe+b/DlMLJVD8gav294kCIbYmXDoRpN4E7O6XWiu/04Nm+A6uCCUOg6gtfAgMBAAECgYBa4Iwp8OEISkwSOx//gGDze1ZhjLSyXYTusSCWoyStg2daHcLxOwSYpdCNAs4AyTLbuLc9cZ8xFAp4maEZ8QMKFpCYDxOtR5dbPENSVmTqoKyVh1eC7xbOrIfnKlYtEk8xcmsgSxRA/sFzQ/1DXjyS8mKBB77Lz3ARNNWFyxOCMQJBANBq/kMBE9l6lV92WcBXcMjRv6r6T1GH+9/QlNEZa7Dx5oJ7QksnpUsN3H2haLhomIbajtqYKjhimw5EsNBvavkCQQDN0nRwu48zacnXfLwY7/VugC1chM3miGAs3jRdILTg9ion6lG1SPlUB7qK+Yu/hUrBqKXimjFcTUW+mq+p16cXAkAqsL/EGcRnF6ThGOzpaP/BIrWP4BW/LYt6HM9exUY5yFLfB0BdBJHm/b/cO+OnYkVb2gjI3cJIKmp4Ubml+CyBAkAQSj7jCUU9OXs7LekpRBy1wTlV4ED3pQWPp4qXcFZAk9skS5xp77ukFfSCuVuBLgiLqVBvzepoNsl1W4LZu3LDAkBWxxyLZ+u9GtxVaqjOBD99ein7dznJb+qwheHBqNdmFlUJDZVWs+0ulbEraVxShlFCKiISjh8mX3na45+Jb/ww";
        String sign = RsaUtil.sign("merchant=MER_YLTJ&time="+dateTime,privateKey);
        map.put("sign",sign);

        String requestData = JSONUtil.toJsonStr(map);
        System.out.println("请求入参："+requestData);
        HttpsClient client = new HttpsClient("application/json;charset=UTF-8");
        String result = client.doPost(url,requestData);
        System.out.println("返回数据："+result);
        Map<String,String> stringMap = JSONUtil.toBean(result,Map.class);
        String resuleData = decryptByPrivateKey(stringMap.get("response"),privateKey);
        System.out.println("解密后数据："+resuleData);
    }
}
