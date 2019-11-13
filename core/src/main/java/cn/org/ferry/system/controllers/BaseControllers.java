package cn.org.ferry.system.controllers;

import cn.org.ferry.system.annotations.LoginPass;
import cn.org.ferry.system.components.TokenTactics;
import cn.org.ferry.system.dto.ResponseData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by 2018-09-11
 * @author ferry email:ferry_sy@163.com wechat:s1194385796
 * @version 1.0
 */

@RestController
public class BaseControllers {
    @Autowired
    private TokenTactics tokenTactics;

    @LoginPass
    @RequestMapping(value = "/api/generate/token", method = RequestMethod.GET)
    public ResponseData generateToken(String userCode, String password){
        ResponseData responseData = new ResponseData();
        if(StringUtils.isEmpty(userCode) || StringUtils.isEmpty(password)){
            responseData.setSuccess(false);
            responseData.setMessage("token认证信息为空!");
            return responseData;
        }
        responseData.setToken(tokenTactics.generateToken(userCode, password));
        responseData.setMessage("token认证成功!");
        return responseData;
    }
}
