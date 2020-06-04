package cn.org.ferry.core.security.jwt;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>json web token 对
 *
 * @author ferry ferry_sy@163.com
 * created by 2020/05/30 14:17
 */

@Data
public class JwtPair implements Serializable {

    public static final String ACCESS_TOKEN = "accessToken";

    public static final String REFRESH_TOKEN = "refreshToken";

    /**
     * 访问token
     */
    private String accessToken;

    /**
     * 刷新token
     */
    private String refreshToken;
}
