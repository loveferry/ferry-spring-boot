package cn.org.ferry.doc.mapper;

import cn.org.ferry.core.mapper.Mapper;
import cn.org.ferry.doc.dto.DocTemplateParam;
import cn.org.ferry.doc.dto.model.DocTemplateParamQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ferry ferry_sy@163.com
 * @date 2019/07/13
 * @description
 */
public interface DocTemplateParamMapper extends Mapper<DocTemplateParam> {
    /**
     * 查询模版参数
     */
    List<DocTemplateParam> query(DocTemplateParamQuery query);

    /**
     * 获取指定模版的参数集
     */
    List<DocTemplateParam> queryByTemplateCode(@Param("templateCode") String templateCode);
}
