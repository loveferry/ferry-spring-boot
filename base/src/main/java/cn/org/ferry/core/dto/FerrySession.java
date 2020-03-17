package cn.org.ferry.core.dto;

import java.util.HashMap;

/**
 * <p>session记录类
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/10/11 14:57
 */

public class FerrySession extends HashMap<String, Object> {
    public static final String SESSION_ID = "sessionId";

    public static final String USER_ID = "userId";

    public static final String USER_NAME_ZH = "userNameZh";

    public static final String USER_NAME_EN = "userNameEn";

    public static final String USER_CODE = "userCode";

    private String sessionId;

    private String userId;

    private String userNameZh;

    private String userNameEn;

    private String userCode;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        super.put(SESSION_ID, sessionId);
        this.sessionId = sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        super.put(USER_ID, userId);
        this.userId = userId;
    }

    public String getUserNameZh() {
        return userNameZh;
    }

    public void setUserNameZh(String userNameZh) {
        super.put(USER_NAME_ZH, userNameZh);
        this.userNameZh = userNameZh;
    }

    public String getUserNameEn() {
        return userNameEn;
    }

    public void setUserNameEn(String userNameEn) {
        super.put(USER_NAME_EN, userNameEn);
        this.userNameEn = userNameEn;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        super.put(USER_CODE, userCode);
        this.userCode = userCode;
    }
}
