package cn.org.ferry.core.security.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * <p>jwt属性类
 *
 * @author ferry ferry_sy@163.com
 * created by 2020/05/30 14:17
 */

@Data
@PropertySource("classpath:config_base.properties")
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    /**
     * jks 路径
     */
    private String keyPath;

    /**
     * 别名
     */
    private String alias;

    /**
     * 密钥库密码
     */
    private String keyPass;

    /**
     * jwt签发者
     */
    private String iss;

    /**
     * jwt所面向的用户
     **/
    private String sub;
    /**
     * access jwt token 有效时长（秒）
     */
    private long accessExp;
    /**
     * refresh jwt token 有效时长（秒）
     */
    private long refreshExp;
}
