package cn.org.ferry.sys.controllers;

import cn.org.ferry.sys.dto.SysAttachmentCategory;
import cn.org.ferry.sys.service.SysAttachmentCategoryService;
import cn.org.ferry.system.annotations.LoginPass;
import cn.org.ferry.system.dto.BaseDTO;
import cn.org.ferry.system.dto.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 附件类型定义控制器
 * @author ferry
 * @date 2019-09-20
 */

@RestController
public class SysAttachmentCategoryController extends BaseDTO {
    @Autowired private SysAttachmentCategoryService sysAttachmentCategoryService;

    /**
     * 更新附件类型定义
     */
    @RequestMapping(value = "/api/attachment/category/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData save(HttpServletRequest httpServletRequest,@RequestBody SysAttachmentCategory sysAttachmentCategory){
        return sysAttachmentCategoryService.save(sysAttachmentCategory);
    }

    /**
     * 查询附件类型
     */
    @LoginPass
    @RequestMapping(value = "/api/attachment/category/query", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData query(SysAttachmentCategory sysAttachmentCategory){
        return  new ResponseData(sysAttachmentCategoryService.query(sysAttachmentCategory));
    }
}
