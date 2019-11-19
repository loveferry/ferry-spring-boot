package cn.org.ferry.system.dto;

import java.util.Date;
import java.util.HashMap;

/**
 * <p>request处理类
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/10/11 14:57
 */

public class FerryRequest extends HashMap<String, Object> {
    private FerrySession ferrySession;

    private Date now;

    private String token;

    public FerryRequest(FerrySession ferrySession){
        this.ferrySession = ferrySession;
        this.now = new Date();
    }

    public FerrySession getFerrySession() {
        return ferrySession;
    }

    public Date getNow() {
        return now;
    }

    public void setNow(Date now) {
        this.now = now;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
