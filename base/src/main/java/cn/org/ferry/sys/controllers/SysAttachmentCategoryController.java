package cn.org.ferry.sys.controllers;

import cn.org.ferry.core.annotations.LoginPass;
import cn.org.ferry.core.dto.BaseDTO;
import cn.org.ferry.core.dto.ResponseData;
import cn.org.ferry.sys.dto.SysAttachmentCategory;
import cn.org.ferry.sys.service.SysAttachmentCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 附件类型定义控制器
 * @author ferry
 * @date 2019-09-20
 */

@RestController
@Api(tags = "附件类型控制器")
public class SysAttachmentCategoryController extends BaseDTO {
    @Autowired
    private SysAttachmentCategoryService sysAttachmentCategoryService;

    /**
     * 更新附件类型定义
     */
    @ApiOperation("更新附件类型")
    @RequestMapping(value = "/api/attachment/category/save", method = RequestMethod.POST)
    public ResponseData save(HttpServletRequest httpServletRequest,@RequestBody SysAttachmentCategory sysAttachmentCategory){
        return sysAttachmentCategoryService.save(sysAttachmentCategory);
    }

    /**
     * 查询附件类型
     */
    @ApiOperation("查询附件类型")
    @LoginPass
    @RequestMapping(value = "/api/attachment/category/query", method = RequestMethod.GET)
    public ResponseData query(SysAttachmentCategory sysAttachmentCategory,
                              @RequestParam(value = "page", defaultValue = "1")int page,
                              @RequestParam(value = "pageSize", defaultValue = "10")int pageSize){
        return  new ResponseData(sysAttachmentCategoryService.query(sysAttachmentCategory, page, pageSize));
    }
}
