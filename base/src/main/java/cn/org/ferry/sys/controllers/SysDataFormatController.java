package cn.org.ferry.sys.controllers;

import cn.org.ferry.sys.service.SysDataFormatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SysDataFormatController {
    @Autowired
    private SysDataFormatService sysDataFormatService;

}
