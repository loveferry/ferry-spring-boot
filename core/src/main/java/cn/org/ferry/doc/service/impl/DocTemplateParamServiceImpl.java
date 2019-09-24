package cn.org.ferry.doc.service.impl;

import cn.org.ferry.doc.dto.DocTemplateParam;
import cn.org.ferry.doc.exceptions.DocException;
import cn.org.ferry.doc.mapper.DocTemplateParamMapper;
import cn.org.ferry.doc.service.DocTemplateParamService;
import cn.org.ferry.system.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import javax.annotation.Resource;

/**
 * @author ferry ferry_sy@163.com
 * @date 2019/07/13
 * @description
 */
@Service
public class DocTemplateParamServiceImpl extends BaseServiceImpl<DocTemplateParam> implements DocTemplateParamService {
    @Resource
    private DocTemplateParamMapper docTemplateParamMapper;

    @Override
    public List<DocTemplateParam> queryByTemplateCode(String templateCode) {
        if(StringUtils.isEmpty(templateCode)){
            throw new DocException("模版编码为空");
        }
        return docTemplateParamMapper.queryByTemplateCode(templateCode);
    }
}
