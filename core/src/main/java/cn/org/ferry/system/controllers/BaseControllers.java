package cn.org.ferry.system.controllers;

import cn.org.ferry.sys.dto.SysUser;
import cn.org.ferry.system.dto.ResponseData;
import cn.org.ferry.system.sysenum.Sex;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * created by 2018-09-11
 * @author ferry email:ferry_sy@163.com wechat:s1194385796
 * @version 1.0
 */

@Controller
public class BaseControllers {

    @RequestMapping("/test1")
    @ResponseBody
    public ResponseData test1(){
        SysUser sysUser = new SysUser();
        sysUser.setUserId(101L);
        sysUser.setUserNameZh("盛玉");
        sysUser.setUserNameEn("ferry");
        sysUser.setUserCode("KBUF");
        sysUser.setUserSex(Sex.MALE);
        List<SysUser> list = new ArrayList<>(1);
        list.add(sysUser);
        return new ResponseData(list);
    }

    @RequestMapping("/test2")
    @ResponseBody
    public SysUser test2(){
        SysUser sysUser = new SysUser();
        sysUser.setUserId(101L);
        sysUser.setUserNameZh("盛玉");
        sysUser.setUserNameEn("ferry");
        sysUser.setUserCode("KBUF");
        sysUser.setUserSex(Sex.MALE);
        return sysUser;
    }
}
