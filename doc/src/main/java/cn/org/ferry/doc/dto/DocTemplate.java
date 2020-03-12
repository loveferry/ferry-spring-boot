package cn.org.ferry.doc.dto;

import cn.org.ferry.core.dto.BaseDTO;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author ferry ferry_sy@163.com
 * @date 2019/07/13
 * @description word文档模版实体类
 */
@Data
@Table(name = "doc_template")
public class DocTemplate extends BaseDTO {
    /**
     * primary key
     */
    @Id
    @GeneratedValue
    private Long templateId;

    /**
     * 模版代码
     */
    private String templateCode;

    /**
     * 模版名称
     */
    private String templateName;

    /**
     * 说明
     */
    private String description;

    /**
     * 图片
     */
    private String templateImage;
}
