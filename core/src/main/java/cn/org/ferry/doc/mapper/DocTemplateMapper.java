package cn.org.ferry.doc.mapper;

import cn.org.ferry.doc.dto.DocTemplate;
import cn.org.ferry.system.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author ferry ferry_sy@163.com
 * @date 2019/07/13
 * @description
 */
public interface DocTemplateMapper extends Mapper<DocTemplate> {
    /**
     * 查找模版
     */
    DocTemplate queryByTemplateCode(@Param("templateCode") String templateCode);
}
