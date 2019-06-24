package cn.org.ferry.system.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

/**
 * token 工具类
 */

public class TokenUtils {
    public static String generateToken(String id, String secret){
        return JWT.create().withAudience(id).sign(Algorithm.HMAC256(secret));
    }
}
