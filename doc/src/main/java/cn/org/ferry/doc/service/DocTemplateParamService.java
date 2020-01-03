package cn.org.ferry.doc.service;

import cn.org.ferry.core.service.BaseService;
import cn.org.ferry.doc.dto.DocTemplateParam;

import java.util.List;

/**
 * @author ferry ferry_sy@163.com
 * @date 2019/07/13
 * @description 文档模版参数业务接口层
 */

public interface DocTemplateParamService extends BaseService<DocTemplateParam> {
    /**
     * 获取指定模版的参数集
     */
    List<DocTemplateParam> queryByTemplateCode(String templateCode);
}
