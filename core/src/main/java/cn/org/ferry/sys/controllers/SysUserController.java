package cn.org.ferry.sys.controllers;

import cn.org.ferry.sys.dto.SysUser;
import cn.org.ferry.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SysUserController {
    @Autowired
    private SysUserService iSysUserService;

    @RequestMapping("/liquibase/sys/user/query")
    @ResponseBody
    public List<SysUser> query(){
        return iSysUserService.select();
    }

    @RequestMapping(value = "/liquibase/sys/user/add")
    @ResponseBody
    public int add(SysUser sysUser){
        return iSysUserService.insertSelective(sysUser);
    }
}
