package cn.org.ferry.doc.controllers;

import cn.org.ferry.core.annotations.LoginPass;
import cn.org.ferry.core.dto.ResponseData;
import cn.org.ferry.doc.dto.model.DocTemplateDefinition;
import cn.org.ferry.doc.enums.BookMarkType;
import cn.org.ferry.doc.service.DocTemplateService;
import cn.org.ferry.doc.utils.Docx4jGenerateUtil;
import cn.org.ferry.sys.exceptions.FileException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ferry ferry_sy@163.com@date 2019/07/13
 * @description 文件模版接口
 */

@Api(tags = "文件模版接口", position = 10)
@RestController
@RequestMapping("/api/doc/template")
@LoginPass
public class DocTemplateController {
    @Autowired
    private DocTemplateService docTemplateService;

    /**
     * 文档模版-查询
     */
    @ApiOperation(value = "文档模版-查询", notes = "根据参数查询出文档模版列表", position = 100)
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public ResponseData query(@ApiParam(name = "condition", value = "模版代码/模版名称")
                              @RequestParam(value = "condition", defaultValue = "") String condition,
                              @ApiParam(name = "page", value = "当前页")
                              @RequestParam(value = "page", defaultValue = "1")int page,
                              @ApiParam(name = "pageSize", value = "页面大小")
                              @RequestParam(value = "pageSize", defaultValue = "10")int pageSize) {
        return new ResponseData(docTemplateService.queryByCondition(condition, page, pageSize));
    }

    /**
     * 文档模版-定义
     */
    @ApiOperation(value = "文档模版-定义", notes = "定义文档模版", position = 90)
    @RequestMapping(value = "/definition", method = RequestMethod.POST)
    public ResponseData definition(@RequestBody DocTemplateDefinition definition) {
        return docTemplateService.definition(definition);
    }

    @ApiOperation(value = "书签替换测试", hidden = true, position = 20)
    @RequestMapping(value = "/replace", method = RequestMethod.POST)
    public void replace(@RequestBody Map<String, Object> map) throws FileException {
        Map<String, Object> bookMarkValue = (Map<String, Object>) (map.get("bookMarkValue"));
        Map<String, String> bookMarkTypeMapStr = (Map<String, String>) (map.get("bookMarkTypeMap"));
        Map<String, BookMarkType> bookMarkTypeMap = new HashMap<>(bookMarkTypeMapStr.size());
        for(Map.Entry<String, String> entry : bookMarkTypeMapStr.entrySet()){
            bookMarkTypeMap.put(entry.getKey(), BookMarkType.valueOf(entry.getValue()));
        }
        String templatePath = String.valueOf(map.get("templatePath"));
        String targetPath = String.valueOf(map.get("targetPath"));
        Docx4jGenerateUtil.generateDocxWithReplaceBookMark(bookMarkValue, bookMarkTypeMap, templatePath, targetPath, true);
    }

    /**
     * 根据模版生成word
     */

    @ApiOperation(value = "书签替换", notes = "word文档的书签替换", position = 30)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "templateCode", value = "模版代码"),
        @ApiImplicitParam(name = "sourceType", value = "目标附件类型"),
        @ApiImplicitParam(name = "sourceKey", value = "目标附件编码"),
        @ApiImplicitParam(name = "params", value = "查询待替换书签的查询来源的参数集合")
    })
    @RequestMapping(value = "/generate", method = RequestMethod.POST)
    public void generateWord(@RequestBody Map<String, Object> map) {
        String templateCode = String.valueOf(map.getOrDefault("templateCode", ""));
        String sourceType = String.valueOf(map.getOrDefault("sourceType", ""));
        String sourceKey = String.valueOf(map.getOrDefault("sourceKey", ""));
        Map<String, Object> params = (Map)map.getOrDefault("params", new HashMap<>(0));
        docTemplateService.generateWord(templateCode, sourceType, sourceKey, params);
    }

}
