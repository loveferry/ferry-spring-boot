package cn.org.ferry.doc.controllers;

import cn.org.ferry.doc.service.DocTemplateParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ferry ferry_sy@163.com
 * @date 2019/07/13
 * @description
 */
@RestController
public class DocTemplateParamController {
    @Autowired
    private DocTemplateParamService docTemplateParamService;
}
