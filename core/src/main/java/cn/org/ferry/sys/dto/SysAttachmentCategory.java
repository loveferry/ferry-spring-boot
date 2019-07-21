package cn.org.ferry.sys.dto;

import cn.org.ferry.system.dto.BaseDTO;
import cn.org.ferry.system.mybatis.handler.EnableFlagHandler;
import cn.org.ferry.system.mybatis.handler.IfOrNotFlagHandler;
import cn.org.ferry.system.sysenum.EnableFlag;
import cn.org.ferry.system.sysenum.IfOrNotFlag;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.ColumnType;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 附件实体类 创建于 2019-07-21
 * @author ferry email:ferry_sy@163.com wechat:s1194385796
 * @version 1.0
 */

@Data
@NoArgsConstructor
@Table(name = "sys_attachment_category")
public class SysAttachmentCategory extends BaseDTO {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long categoryId;  // primary key

    private String categoryName;   // 类型名称

    private String sourceType;  // 附件类型

    @ColumnType(typeHandler = EnableFlagHandler.class)
    private EnableFlag enableFlag;  // 启用标志

    @ColumnType(typeHandler = IfOrNotFlagHandler.class)
    private IfOrNotFlag uniqueFlag;  // 启用标志
}
