package cn.org.ferry.doc.mapper;

import cn.org.ferry.doc.dto.DocTemplateParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ferry ferry_sy@163.com
 * @date 2019/07/13
 * @description
 */
public interface DocTemplateParamMapper{
    /**
     * 获取指定模版的参数集
     */
    List<DocTemplateParam> queryByTemplateCode(@Param("templateCode") String templateCode);
}
