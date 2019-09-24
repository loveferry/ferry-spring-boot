package cn.org.ferry.doc.dto;

import cn.org.ferry.system.dto.BaseDTO;
import cn.org.ferry.system.mybatis.handler.BookMarkTypeHandler;
import lombok.Data;
import tk.mybatis.mapper.annotation.ColumnType;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author ferry ferry_sy@163.com
 * @date 2019/07/13
 * @description word文档模版参数实体类
 */
@Data
@Table(name = "doc_template_param")
public class DocTemplateParam extends BaseDTO {
    /**
     * primary key
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long paramId;

    /**
     * 模版代码
     */
    private String templateCode;

    /**
     * 参数类型
     */
    @ColumnType(typeHandler = BookMarkTypeHandler.class)
    private BookMarkType paramType;

    /**
     * 参数名称
     */
    private String paramName;

    /**
     * 书签名称
     */
    private String bookMark;

    /**
     * 数据源代码
     */
    private String sqlCode;
}
