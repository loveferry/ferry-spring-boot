package cn.org.ferry.doc.service;

import cn.org.ferry.doc.dto.DocTemplateParam;
import cn.org.ferry.system.service.BaseService;

import java.util.List;

/**
 * @author ferry ferry_sy@163.com
 * @date 2019/07/13
 * @description
 */
public interface DocTemplateParamService extends BaseService<DocTemplateParam> {
    /**
     * 获取指定模版的参数集
     */
    List<DocTemplateParam> queryByTemplateCode(String templateCode);
}
