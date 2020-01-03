package cn.org.ferry.doc.controllers;

import cn.org.ferry.core.annotations.LoginPass;
import cn.org.ferry.doc.enums.BookMarkType;
import cn.org.ferry.doc.service.DocTemplateService;
import cn.org.ferry.doc.utils.Docx4jGenerateUtil;
import cn.org.ferry.sys.exceptions.FileException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ferry ferry_sy@163.com@date 2019/07/13
 * @description 文件模版接口
 */

@Api(tags = "文件模版接口")
@RestController
public class DocTemplateController {
    @Autowired
    private DocTemplateService docTemplateService;

    @ApiOperation(value = "书签替换测试", hidden = true)
    @LoginPass
    @RequestMapping(value = "/api/docx4j/replace", method = RequestMethod.POST)
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

    @ApiOperation(value = "书签替换", notes = "word文档的书签替换")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "templateCode", value = "模版代码"),
        @ApiImplicitParam(name = "sourceType", value = "目标附件类型"),
        @ApiImplicitParam(name = "sourceKey", value = "目标附件编码"),
        @ApiImplicitParam(name = "params", value = "查询待替换书签的查询来源的参数集合")
    })
    @RequestMapping(value = "/api/doc/generate", method = RequestMethod.POST)
    public void generateWord(@RequestBody Map<String, Object> map) {
        String templateCode = String.valueOf(map.getOrDefault("templateCode", ""));
        String sourceType = String.valueOf(map.getOrDefault("sourceType", ""));
        String sourceKey = String.valueOf(map.getOrDefault("sourceKey", ""));
        Map<String, Object> params = (Map)map.getOrDefault("params", new HashMap<>(0));
        docTemplateService.generateWord(templateCode, sourceType, sourceKey, params);
    }
}
