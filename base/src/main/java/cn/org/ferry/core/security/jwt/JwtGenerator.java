package cn.org.ferry.core.security.jwt;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.jwt.crypto.sign.SignatureVerifier;
import org.springframework.util.Assert;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>jwt生成器
 *
 * @author ferry ferry_sy@163.com
 * created by 2020/05/30 14:17
 */

public class JwtGenerator {


    private JwtProperties jwtProperties;

    private JwtCache jwtCache;

    private KeyPair keyPair;

    /**
     * jwt 生成器的构造方法
     */
    public JwtGenerator(JwtCache jwtCache, JwtProperties jwtProperties) {
        this.jwtCache = jwtCache;
        this.jwtProperties = jwtProperties;
        this.keyPair = KeyPairFactory.generate(
                jwtProperties.getKeyPath(),
                jwtProperties.getAlias(),
                jwtProperties.getKeyPass()
        );
    }


    /**
     * generate json web token pair
     * @param aud 目标用户
     * @param authorities 权限集
     * @param additional 额外的属性
     */
    public JwtPair jwtPair(String aud, Collection<GrantedAuthority> authorities, JSONObject additional) {
        Set<String> authoritySet = new HashSet<>();
        if (CollectionUtil.isNotEmpty(authorities)) {
            for (GrantedAuthority authority : authorities) {
                authoritySet.add(authority.getAuthority());
            }
        }
        JwtPair jwtPair = new JwtPair();
        jwtPair.setAccessToken(jwtToken(aud, jwtProperties.getAccessExp(), authoritySet, additional));
        jwtPair.setRefreshToken(jwtToken(aud, jwtProperties.getRefreshExp(), authoritySet, additional));
        // 放入缓存
        jwtCache.put(jwtPair, aud);
        return jwtPair;
    }


    /**
     * 生成token
     * @param aud 接收jwt的一方
     * @param exp jwt的过期时间，这个过期时间必须要大于签发时间
     * @param authorities 权限集
     * @param additional 附加的属性
     * @return 生成的token
     */
    private String jwtToken(String aud, long exp, Set<String> authorities, JSONObject additional) {
        String payload = JwtPayload
                .builder()
                .iss(jwtProperties.getIss())
                .sub(jwtProperties.getSub())
                .aud(aud)
                .additional(additional)
                .authorities(authorities)
                .exp(exp)
                .build()
                .toJSON();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        RsaSigner signer = new RsaSigner(privateKey);
        return JwtHelper.encode(payload, signer).getEncoded();
    }


    /**
     * 解码 并校验签名 过期不予解析
     */
    public JSONObject decode(String token) {
        Assert.hasText(token, "jwt token must not be bank");
        RSAPublicKey rsaPublicKey = (RSAPublicKey) this.keyPair.getPublic();
        SignatureVerifier rsaVerifier = new RsaVerifier(rsaPublicKey);
        Jwt jwt = JwtHelper.decodeAndVerify(token, rsaVerifier);
        return JSON.parseObject(jwt.getClaims());
    }
}
