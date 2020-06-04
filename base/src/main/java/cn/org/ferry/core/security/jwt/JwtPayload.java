package cn.org.ferry.core.security.jwt;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections4.MapUtils;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>构建 jwt payload
 *
 * @author ferry ferry_sy@163.com
 * created by 2020/05/30 14:17
 */

public class JwtPayload {
    public static final String ISS = "iss";
    public static final String SUB = "sub";
    public static final String AUD = "aud";
    public static final String IAT = "iat";
    public static final String EXP = "exp";
    public static final String AUTHORITIES = "authorities";
    public static final String JTI = "jti";
    public static final String ADDITIONAL = "additional";

    private JSONObject payload = new JSONObject(10);

    /**
     * jwt签发者
     */
    private String iss;

    /**
     * jwt所面向的用户
     */
    private String sub;

    /**
     * 接收jwt的一方
     */
    private String aud;

    /**
     * jwt的签发时间
     */
    private LocalDateTime iat;

    /**
     * jwt的过期时间，这个过期时间必须要大于签发时间
     */
    private LocalDateTime exp;

    /**
     * 权限集
     */
    private Set<String> authorities;

    /**
     * jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击
     */
    private String jti;

    /**
     * 附加的属性
     */
    private JSONObject additional;

    private JwtPayload(String iss, String sub, String aud, LocalDateTime iat,
                       LocalDateTime exp, Set<String> authorities, String jti, JSONObject additional){
        this.iss = iss;
        this.sub = sub;
        this.aud = aud;
        this.iat = iat;
        this.exp = exp;
        this.authorities = authorities;
        this.jti = jti;
        this.additional = additional;
    }

    public static JwtPayloadBuilder builder(){
        return new JwtPayloadBuilder();
    }

    public String toJSON(){
        payload.put(ISS, this.iss);
        payload.put(SUB, this.sub);
        payload.put(AUD, this.aud);
        payload.put(EXP, this.exp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        payload.put(IAT, this.iat.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        payload.put(JTI, this.jti);

        if (!MapUtils.isEmpty(additional)) {
            payload.putAll(additional);
        }
        payload.put(AUTHORITIES, JSONObject.toJSONString(this.authorities));
        return payload.toJSONString();
    }

    public static class JwtPayloadBuilder{
        /**
         * 附加的属性
         */
        private JSONObject additional;

        /**
         * jwt签发者
         */
        private String iss;

        /**
         * jwt所面向的用户
         */
        private String sub;

        /**
         * 接收jwt的一方
         */
        private String aud;

        /**
         * jwt的过期时间，这个过期时间必须要大于签发时间
         */
        private LocalDateTime exp;

        /**
         * jwt的签发时间
         */
        private LocalDateTime iat = LocalDateTime.now();

        /**
         * 权限集
         */
        private Set<String> authorities = new HashSet<>();

        /**
         * jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击
         */
        private String jti = IdUtil.simpleUUID();

        public JwtPayloadBuilder iss(String iss) {
            this.iss = iss;
            return this;
        }


        public JwtPayloadBuilder sub(String sub) {
            this.sub = sub;
            return this;
        }

        public JwtPayloadBuilder aud(String aud) {
            this.aud = aud;
            return this;
        }


        public JwtPayloadBuilder authorities(Set<String> authorities) {
            this.authorities = authorities;
            return this;
        }

        public JwtPayloadBuilder exp(long second) {
            Assert.isTrue(second > 0, "jwt expireDate must after now");
            this.exp = this.iat.plusSeconds(second);
            return this;
        }

        public JwtPayloadBuilder additional(JSONObject additional) {
            this.additional = additional;
            return this;
        }

        public JwtPayload build() {
            return new JwtPayload(this.iss, this.sub, this.aud, this.iat, this.exp, this.authorities, this.jti, this.additional);
        }
    }
}

