package cn.org.ferry.sys.controllers;

import cn.org.ferry.core.dto.ResponseData;
import cn.org.ferry.sys.service.SysSqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SysSqlController {
    @Autowired
    private SysSqlService sysSqlService;

    @RequestMapping(value = "/api/sys/sql/execute", method = RequestMethod.POST)
    public ResponseData execute(String sql) throws SQLException {
        ResponseData responseData = new ResponseData();
        Map<String, Object> params = new HashMap<>();
        params.put("userCode", "FERRY");
        Object o = sysSqlService.execute(sql, params);
        if(o instanceof List){
            responseData.setMaps((List)o);
        }else{
            List list = new ArrayList();
            list.add(o);
            responseData.setMaps(list);
        }
        return responseData;
    }
}
