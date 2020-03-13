package cn.org.ferry.sys.dto.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 附件类型上传类 创建于 2020-03-12
 * @author ferry email:ferry_sy@163.com wechat:s1194385796
 * @version 1.0
 */

@Data
@ApiModel(value = "附件类型上传类")
public class SysAttachmentUploadOrDownload {
    @ApiModelProperty(value = "附件类型", position = 20)
    private String sourceType;

    @ApiModelProperty(value = "附件业务编码", position = 30)
    private String sourceKey;
}
