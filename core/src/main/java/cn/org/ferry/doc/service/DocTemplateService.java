package cn.org.ferry.doc.service;

import cn.org.ferry.doc.dto.DocTemplate;
import cn.org.ferry.sys.dto.SysFile;
import cn.org.ferry.system.service.BaseService;

/**
 * @author ferry ferry_sy@163.com
 * @date 2019/07/13
 * @description
 */
public interface DocTemplateService extends BaseService<DocTemplate> {
    /**
     * 根据模版参数生成word文档
     */
    SysFile generateWord(DocTemplate docTemplate);
}
