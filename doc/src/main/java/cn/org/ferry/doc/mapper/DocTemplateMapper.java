package cn.org.ferry.doc.mapper;

import cn.org.ferry.core.mapper.Mapper;
import cn.org.ferry.doc.dto.DocTemplate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ferry ferry_sy@163.com
 * @date 2019/07/13
 * @description
 */
public interface DocTemplateMapper extends Mapper<DocTemplate> {

    /**
     * 查询模版
     * condition => 代码/名称
     */
    List<DocTemplate> queryByCondition(@Param("condition") String condition);

    /**
     * 查找模版
     */
    DocTemplate queryByTemplateCode(@Param("templateCode") String templateCode);
}
