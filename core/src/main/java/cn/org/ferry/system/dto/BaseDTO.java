package cn.org.ferry.system.dto;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * 基础字段 创建于 2018-09-11
 * @author ferry email:ferry_sy@163.com wechat:s1194385796
 * @version 1.0
 */

public class BaseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String CREATEDBY = "createdBy";
    public static final String CREATIONDATE = "creationDate";
    public static final String LASTUPDATEDBY = "lastUpdatedBy";
    public static final String LASTUPDATEDATE = "lastUpdateDate";

    private Long createdBy;

    private Date creationDate;

    private Long lastUpdatedBy;

    private Date lastUpdateDate;

    @Transient
    private String _token;

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String get_token() {
        return _token;
    }

    public void set_token(String _token) {
        this._token = _token;
    }
}
