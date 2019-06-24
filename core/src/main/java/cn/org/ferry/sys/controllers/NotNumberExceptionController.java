package cn.org.ferry.sys.controllers;

import cn.org.ferry.system.annotation.LoginPass;
import cn.org.ferry.system.exception.NotNumberException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
@LoginPass
public class NotNumberExceptionController {

    /**
     * 测试异常
     */
    @RequestMapping("/exception")
    @ResponseBody
    public void exception() throws NotNumberException {
        throw new NotNumberException();
    }
}
