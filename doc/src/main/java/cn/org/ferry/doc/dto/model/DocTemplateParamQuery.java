package cn.org.ferry.doc.dto.model;

import cn.org.ferry.doc.enums.BookMarkType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ferry ferry_sy@163.com
 * @date 2020/03/24
 * @description 文档模版参数查询类
 */
@Data
@ApiModel(value = "文档模版参数查询类")
public class DocTemplateParamQuery {
    @ApiModelProperty(value = "模版代码", position = 10)
    private String templateCode;

    @ApiModelProperty(value = "参数名称", position = 20)
    private String paramName;

    @ApiModelProperty(value = "书签名称", position = 30)
    private String bookMark;

    @ApiModelProperty(value = "参数类型", position = 40)
    private BookMarkType paramType;

    @ApiModelProperty(value = "数据源", position = 50)
    private String sqlName;
}
