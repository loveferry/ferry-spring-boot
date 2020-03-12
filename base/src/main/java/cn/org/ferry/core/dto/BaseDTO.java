package cn.org.ferry.core.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;

/**
 * 基础字段 创建于 2018-09-11
 * @author ferry email:ferry_sy@163.com wechat:s1194385796
 * @version 1.0
 */

@Entity
public class BaseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String CREATED_BY = "createdBy";
    public static final String CREATION_DATE = "creationDate";
    public static final String LAST_UPDATED_BY = "lastUpdatedBy";
    public static final String LAST_UPDATE_DATE = "lastUpdateDate";

    private Long createdBy;

    private Date creationDate;

    private Long lastUpdatedBy;

    private Date lastUpdateDate;

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
}
