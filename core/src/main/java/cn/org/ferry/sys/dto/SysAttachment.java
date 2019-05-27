package cn.org.ferry.sys.dto;

import cn.org.ferry.system.dto.BaseDTO;
import cn.org.ferry.system.mybatis.handler.EnableFlagHandler;
import cn.org.ferry.system.sysenum.EnableFlag;
import lombok.Data;
import tk.mybatis.mapper.annotation.ColumnType;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 附件实体类 创建于 2018-12-21
 * @author ferry email:ferry_sy@163.com wechat:s1194385796
 * @version 1.0
 */

@Data
@Table(name = "sys_attachment")
public class SysAttachment extends BaseDTO {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long attachmentId;  // primary key

    private String attachmentName;   // 附件名称

    private String sourceType;  // 附件类型

    private String sourceKey;   // 附件业务编码

    @ColumnType(typeHandler = EnableFlagHandler.class)
    private EnableFlag enableFlag;  // 启用标志

}
