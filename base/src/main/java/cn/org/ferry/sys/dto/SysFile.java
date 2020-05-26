package cn.org.ferry.sys.dto;

import cn.org.ferry.core.dto.BaseDTO;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 文件实体类 创建于 2018-09-22
 * @author ferry email:ferry_sy@163.com wechat:s1194385796
 * @version 1.0
 */

@Data
@Table(name = "sys_file")
public class SysFile extends BaseDTO {
    @Id
    @GeneratedValue
    private Long fileId;  // primary key

    private Long attachmentId;  // 附件id

    private String fileName;  // 文件名

    private String filePath;   // 文件绝对路径

    private Long fileSize;    // 文件大小(字节)

    private String fileType;   // 文件类型

    @Transient
    private String categoryName;  // 类型名称

    @Transient
    private String sourceKey;  // 附件编码

    @Transient
    private String sourceType;  // 附件类别



}
