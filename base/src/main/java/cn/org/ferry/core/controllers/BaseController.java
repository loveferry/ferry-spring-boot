package cn.org.ferry.core.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * created by 2018-09-11
 * @author ferry email:ferry_sy@163.com wechat:s1194385796
 * @version 1.0
 */

@Controller
public class BaseController {
    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * swagger 地址跳转
     */
    @RequestMapping(value = "/swagger")
    public String index() {
        logger.info("redirect:swagger-ui.html");
        return "redirect:swagger-ui.html";
    }
}
