package cn.org.ferry.sys.controllers;

import cn.org.ferry.sys.service.SysSqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SysSqlController {
    @Autowired
    private SysSqlService sysSqlService;

}
