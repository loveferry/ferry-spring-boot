package cn.org.ferry.sys.dto;

import cn.org.ferry.core.dto.BaseDTO;
import cn.org.ferry.mybatis.enums.EnableFlag;
import cn.org.ferry.mybatis.enums.IfOrNotFlag;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 附件实体类 创建于 2019-07-21
 * @author ferry email:ferry_sy@163.com wechat:s1194385796
 * @version 1.0
 */

@Data
@Table(name = "sys_attachment_category")
public class SysAttachmentCategory extends BaseDTO {
    @Id
    @GeneratedValue
    private Long categoryId;  // primary key

    private String categoryName;   // 类型名称

    private String sourceType;  // 附件类型

    private String attachmentPath;  // 附件相对目录

    private EnableFlag enableFlag;  // 启用标志

    private IfOrNotFlag uniqueFlag;  // 唯一标志
}
