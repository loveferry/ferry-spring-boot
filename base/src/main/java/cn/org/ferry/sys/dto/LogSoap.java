package cn.org.ferry.sys.dto;

import cn.org.ferry.core.dto.BaseDTO;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Generate by code generator
 * soap接口日志记录表
 */

@Data
@Table(name = "log_soap")
public class LogSoap extends BaseDTO {
    @Id
    @GeneratedValue
    private Long logId;

    /**
     * 服务地址
     */
    @NotNull
    @Length(max = 200)
    private String serviceAddress;

    /**
     * 操作名称
     */
    @NotNull
    @Length(max = 200)
    private String operationName;

    /**
     * 客户端地址
     */
    @Length(max = 200)
    private String clientAddress;

    /**
     * 协议头
     */
    @Length(max = 1000)
    private String protocolHeaders;

    /**
     * 媒体类型
     */
    @Length(max = 100)
    private String contentType;

    /**
     * 流入内容
     */
    private String inputContent;

    /**
     * 流出内容
     */
    private String outputContent;
}
