package cn.org.ferry.sys.controllers;

import cn.org.ferry.core.annotations.LoginPass;
import cn.org.ferry.core.dto.ResponseData;
import cn.org.ferry.sys.dto.SysDataFormat;
import cn.org.ferry.sys.service.SysDataFormatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据格式化控制器
 */
@Api(tags = "数据格式化控制器", position = 1200)
@RestController
@RequestMapping("/api")
public class SysDataFormatController {
    @Autowired
    private SysDataFormatService sysDataFormatService;

    /**
     * 用户信息查询
     */
    @ApiOperation(value = "数据格式化数据查询", position = 1210)
    @LoginPass
    @RequestMapping(value = "/sys/data/format/query", method = RequestMethod.GET)
    public ResponseData query(SysDataFormat sysDataFormat,
                              @ApiParam(name = "page", value = "当前页") @RequestParam(defaultValue = "1") int page,
                              @ApiParam(name = "pageSize", value = "页面大小") @RequestParam(defaultValue = "10") int pageSize){
        return new ResponseData(sysDataFormatService.select(sysDataFormat, page, pageSize));
    }
}
