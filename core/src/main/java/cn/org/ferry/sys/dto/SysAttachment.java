package cn.org.ferry.sys.dto;

import cn.org.ferry.system.dto.BaseDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 附件实体类 创建于 2018-12-21
 * @author ferry email:ferry_sy@163.com wechat:s1194385796
 * @version 1.0
 */

@Data
@NoArgsConstructor
@Table(name = "sys_attachment")
public class SysAttachment extends BaseDTO {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long attachmentId;  // primary key

    private String sourceType;  // 附件类型

    private String sourceKey;   // 附件业务编码
}
