package cn.org.ferry.doc.service.impl;

import cn.org.ferry.doc.dto.DocTemplate;
import cn.org.ferry.doc.dto.DocTemplateParam;
import cn.org.ferry.doc.mapper.DocTemplateMapper;
import cn.org.ferry.doc.mapper.DocTemplateParamMapper;
import cn.org.ferry.doc.service.DocTemplateParamService;
import cn.org.ferry.doc.service.DocTemplateService;
import cn.org.ferry.system.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ferry ferry_sy@163.com
 * @date 2019/07/13
 * @description
 */
@Service
public class DocTemplateParamServiceImpl extends BaseServiceImpl<DocTemplateParam> implements DocTemplateParamService {
    @Autowired
    private DocTemplateParamMapper docTemplateParamMapper;
}