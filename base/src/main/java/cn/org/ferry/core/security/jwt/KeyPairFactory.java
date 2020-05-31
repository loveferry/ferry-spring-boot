package cn.org.ferry.core.security.jwt;

import org.springframework.core.io.ClassPathResource;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.RSAPublicKeySpec;

/**
 * <p>密钥对工厂类
 *
 * @author ferry ferry_sy@163.com
 * created by 2020/05/30 14:17
 */

class KeyPairFactory {

    /**
     *  keytool -genkey -alias ferry -keypass loveferry -storetype PKCS12 -keyalg RSA -keysize 1024 -validity 1 -keystore /Users/ferry/ferry.jks -storepass loveferry  -dname "CN=(ferry), OU=(ferry), O=(ferry), L=(suzhou), ST=(jiangsu), C=(cn)"
     */

    /**
     * 获取公私钥.
     *
     * @param keyPath  证书路径
     * @param alias -alias 别名
     * @param storePass  -storepass 指定的值， 密钥库的密码
     * @return 密钥对
     */
    synchronized static KeyPair generate(String keyPath, String alias, String storePass) {
        ClassPathResource resource = new ClassPathResource(keyPath);
        char[] pem = storePass.toCharArray();
        try {
            KeyStore keyStore = KeyStore.getInstance("jks");
            keyStore.load(resource.getInputStream(), pem);
            RSAPrivateCrtKey key = (RSAPrivateCrtKey) keyStore.getKey(alias, pem);
            RSAPublicKeySpec spec = new RSAPublicKeySpec(key.getModulus(), key.getPublicExponent());
            PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(spec);
            return new KeyPair(publicKey, key);
        } catch (Exception e) {
            throw new IllegalStateException("Cannot load keys from store: " + resource, e);
        }

    }
}
