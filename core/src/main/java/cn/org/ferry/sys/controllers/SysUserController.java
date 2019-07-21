package cn.org.ferry.sys.controllers;

import cn.org.ferry.sys.dto.SysUser;
import cn.org.ferry.sys.service.SysUserService;
import cn.org.ferry.system.annotation.LoginPass;
import cn.org.ferry.system.dto.ResponseData;
import cn.org.ferry.system.utils.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    /**
     * 登陆只能通过员工代码登陆
     * @param sysUser 员工代码和密码为必传项
     * @return 返回登陆信息
     */
    @LoginPass
    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData login(@RequestBody SysUser sysUser){
        String password = sysUser.getPassword();
        ResponseData responseData = new ResponseData();
        sysUser = sysUserService.queryByUserCode(sysUser.getUserCode());
        if(sysUser == null){
            responseData.setSuccess(false);
            responseData.setMessage("用户不存在或密码输入错误!");
            return responseData;
        }
        if(!StringUtils.equals(password, sysUser.getPassword())){
            responseData.setSuccess(false);
            responseData.setMessage("用户不存在或密码输入错误!");
            return responseData;
        }
        responseData.setToken(TokenUtils.generateToken(sysUser.getUserCode(), sysUser.getPassword()));
        responseData.setMessage("登陆成功!");
        return responseData;
    }
}
