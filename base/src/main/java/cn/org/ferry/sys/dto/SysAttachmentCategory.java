package cn.org.ferry.sys.dto;

import cn.org.ferry.core.dto.BaseDTO;
import cn.org.ferry.mybatis.annotations.ColumnType;
import cn.org.ferry.mybatis.enums.IfOrNot;
import cn.org.ferry.mybatis.handlers.IfOrNotHandler;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;

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

    @ColumnType(jdbcType = JdbcType.VARCHAR, typeHandler = IfOrNotHandler.class)
    private IfOrNot enabledFlag;  // 启用标志

    @ColumnType(jdbcType = JdbcType.VARCHAR, typeHandler = IfOrNotHandler.class)
    private IfOrNot uniqueFlag;  // 唯一标志
}
