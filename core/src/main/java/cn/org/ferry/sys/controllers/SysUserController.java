package cn.org.ferry.sys.controllers;

import cn.org.ferry.sys.dto.SysUser;
import cn.org.ferry.sys.service.SysUserService;
import cn.org.ferry.system.dto.ResponseData;
import cn.org.ferry.system.utils.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("/login")
    @ResponseBody
    public ResponseData login(@RequestBody SysUser sysUser){
        String password = sysUser.getPassword();
        ResponseData responseData = new ResponseData();
        sysUser = sysUserService.queryByUserNameZh(sysUser.getUserNameZh());
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
