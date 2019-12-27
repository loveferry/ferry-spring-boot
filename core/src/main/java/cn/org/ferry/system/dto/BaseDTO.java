package cn.org.ferry.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础字段 创建于 2018-09-11
 * @author ferry email:ferry_sy@163.com wechat:s1194385796
 * @version 1.0
 */

@ApiModel(value = "基础字段类")
public class BaseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String CREATED_BY = "createdBy";
    public static final String CREATION_DATE = "creationDate";
    public static final String LAST_UPDATED_BY = "lastUpdatedBy";
    public static final String LAST_UPDATE_DATE = "lastUpdateDate";

    @ApiModelProperty(value = "创建人主键", position = 65530, readOnly = true, allowEmptyValue = true)
    private Long createdBy;

    @ApiModelProperty(value = "创建时间", position = 65531, readOnly = true)
    private Date creationDate;

    @ApiModelProperty(value = "最后更新人主键", position = 65532, readOnly = true, allowEmptyValue = true)
    private Long lastUpdatedBy;

    @ApiModelProperty(value = "最后更新时间", position = 65533, readOnly = true)
    private Date lastUpdateDate;

    @ApiModelProperty(value = "token", position = 65534, readOnly = true)
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
