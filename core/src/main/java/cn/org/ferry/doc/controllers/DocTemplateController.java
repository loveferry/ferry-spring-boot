package cn.org.ferry.doc.controllers;

import cn.org.ferry.doc.dto.BookMarkType;
import cn.org.ferry.doc.service.DocTemplateService;
import cn.org.ferry.doc.utils.Docx4jGenerateUtil;
import cn.org.ferry.system.annotations.LoginPass;
import cn.org.ferry.system.exception.FileException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ferry ferry_sy@163.com
 * @date 2019/07/13
 * @description
 */
@RestController
public class DocTemplateController {
    @Autowired
    private DocTemplateService docTemplateService;

    @LoginPass
    @RequestMapping(value = "/api/docx4j/replace", method = RequestMethod.POST)
    @ResponseBody
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
    @RequestMapping(value = "/api/doc/generate", method = RequestMethod.POST)
    @ResponseBody
    public void generateWord(@RequestBody Map<String, Object> map) {
        String templateCode = String.valueOf(map.getOrDefault("templateCode", ""));
        String sourceType = String.valueOf(map.getOrDefault("sourceType", ""));
        String sourceKey = String.valueOf(map.getOrDefault("sourceKey", ""));
        Map<String, Object> params = (Map)map.getOrDefault("params", new HashMap<>(0));
        docTemplateService.generateWord(templateCode, sourceType, sourceKey, params);
    }
}
