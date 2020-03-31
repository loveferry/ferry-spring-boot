package cn.org.ferry.doc.controllers;

import cn.org.ferry.core.annotations.LoginPass;
import cn.org.ferry.core.dto.ResponseData;
import cn.org.ferry.doc.dto.model.DocTemplateParamQuery;
import cn.org.ferry.doc.service.DocTemplateParamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ferry ferry_sy@163.com
 * @date 2019/07/13
 * @description 文件模版参数控制器
 */

@RestController
@RequestMapping("/api/doc/template/param")
@Api(tags = "文件模版参数控制器", position = 20)
@LoginPass
public class DocTemplateParamController {
    @Autowired
    private DocTemplateParamService docTemplateParamService;

    /**
     * 文档模版参数-查询
     */
    @ApiOperation(value = "文档模版参数-查询", notes = "查询文档模版列表", position = 100)
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public ResponseData query(DocTemplateParamQuery query,
                              @ApiParam(name = "page", value = "当前页")
                              @RequestParam(value = "page", defaultValue = "1")int page,
                              @ApiParam(name = "pageSize", value = "页面大小")
                              @RequestParam(value = "pageSize", defaultValue = "10")int pageSize) {
        return new ResponseData(docTemplateParamService.query(query, page, pageSize));
    }
}
