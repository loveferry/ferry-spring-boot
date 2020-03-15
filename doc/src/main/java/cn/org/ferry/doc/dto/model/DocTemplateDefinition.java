package cn.org.ferry.doc.dto.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ferry ferry_sy@163.com
 * @date 2019/07/13
 * @description word文档模版实体类
 */
@Data
@ApiModel(value = "文档模版定义类")
public class DocTemplateDefinition {
    @ApiModelProperty(value = "模版主键", position = 10)
    private String templateId;

    @ApiModelProperty(value = "模版代码", position = 20)
    private String templateCode;

    @ApiModelProperty(value = "模版名称", position = 30)
    private String templateName;

    @ApiModelProperty(value = "说明", position = 40)
    private String description;

    @ApiModelProperty(value = "图片地址", position = 50)
    private String templateImage;
}
