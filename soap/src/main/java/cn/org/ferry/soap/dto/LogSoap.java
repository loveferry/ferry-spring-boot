package cn.org.ferry.soap.dto;

import cn.org.ferry.core.dto.BaseDTO;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Generate by code generator
 * soap接口日志记录表
 */

@Table(name = "log_soap")
public class LogSoap extends BaseDTO {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long logId;

    /**
     * 日志类型
     */
    @NotNull
    @Length(max = 200)
    private String logType;

    /**
     * 接口名称
     */
    @NotNull
    @Length(max = 200)
    private String serviceName;

    /**
     * 接口名称
     */
    @NotNull
    @Length(max = 200)
    private String operationName;

    /**
     * 访问路径
     */
    @Length(max = 200)
    private String url;

    /**
     * 协议头
     */
    @Length(max = 1000)
    private String protocolHeaders;

    /**
     * 请求方式
     */
    @Length(max = 10)
    private String httpMethod;

    /**
     * 媒体类型
     */
    @Length(max = 100)
    private String contentType;

    /**
     * 编码格式
     */
    @Length(max = 20)
    private String encoding;

    /**
     * 内容
     */
    private String content;

    public LogSoap() {
    }

    public LogSoap(@NotNull @Length(max = 200) String logType, @NotNull @Length(max = 200) String serviceName,
                   @NotNull @Length(max = 200) String operationName, @Length(max = 200) String url,
                   @Length(max = 1000) String protocolHeaders, @Length(max = 10) String httpMethod,
                   @Length(max = 100) String contentType, @Length(max = 20) String encoding, String content) {
        this.logType = logType;
        this.serviceName = serviceName;
        this.operationName = operationName;
        this.url = url;
        this.protocolHeaders = protocolHeaders;
        this.httpMethod = httpMethod;
        this.contentType = contentType;
        this.encoding = encoding;
        this.content = content;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Long getLogId() {
        return this.logId;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getLogType() {
        return this.logType;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    public void setProtocolHeaders(String protocolHeaders) {
        this.protocolHeaders = protocolHeaders;
    }

    public String getProtocolHeaders() {
        return this.protocolHeaders;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getHttpMethod() {
        return this.httpMethod;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return this.contentType;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getEncoding() {
        return this.encoding;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

}
