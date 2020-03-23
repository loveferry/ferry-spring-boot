package cn.org.ferry.doc.service;

import cn.org.ferry.core.dto.ResponseData;
import cn.org.ferry.core.service.BaseService;
import cn.org.ferry.doc.dto.DocTemplate;
import cn.org.ferry.doc.dto.model.DocTemplateDefinition;

import java.util.List;
import java.util.Map;

/**
 * @author ferry ferry_sy@163.com
 * @date 2019/07/13
 * @description 文档模版业务接口层
 */

public interface DocTemplateService extends BaseService<DocTemplate> {
    /**
     * 文档模版的附件类型
     */
    String DOC_TEMPLATE_ATTACHMENT_CATEGORY = "DOC_TEMPLATE";

    /**
     * 查询模版
     * condition => 代码/名称
     */
    List<DocTemplate> queryByCondition(String condition, int page, int pageSize);

    /**
     * 文档模版-定义
     */
    ResponseData definition(DocTemplateDefinition definition);

    /**
     * 查找模版
     */
    DocTemplate queryByTemplateCode(String templateCode);

    /**
     * 根据模版参数生成word文档
     * @param templateCode 模版代码
     * @param sourceType 目标附件代码
     * @param sourceKey 目标附件编码
     * @param params 模版书签各个数据源的参数集
     */
    void generateWord(String templateCode, String sourceType, String sourceKey, Map<String, Object> params);


}
