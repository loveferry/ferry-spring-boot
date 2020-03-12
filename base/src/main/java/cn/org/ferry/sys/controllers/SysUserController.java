package cn.org.ferry.sys.controllers;

import cn.org.ferry.core.annotations.LoginPass;
import cn.org.ferry.core.dto.ResponseData;
import cn.org.ferry.sys.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "用户控制器")
@RestController(value = "/api")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    /**
     * 用户信息查询
     */
    @ApiOperation("用户信息查询")
    @LoginPass
    @RequestMapping(value = "/sys/user/query", method = RequestMethod.GET)
    public ResponseData query(@ApiParam(name = "userNameEn", value = "英文名") @RequestParam(value = "userNameEn",required = false)String userNameEn,
                              @ApiParam(name = "userNameZh", value = "中文名") @RequestParam(value = "userNameZh", required = false)String userNameZh){
        return new ResponseData(sysUserService.query(userNameEn, userNameZh));
    }
}
