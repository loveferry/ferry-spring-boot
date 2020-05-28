package cn.org.ferry.sys.controllers;

import cn.org.ferry.core.dto.ResponseData;
import cn.org.ferry.sys.service.SysSqlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "数据源控制器", position = 1100)
@RestController
public class SysSqlController {
    @Autowired
    private SysSqlService sysSqlService;

    @ApiOperation(value = "执行sql语句", position = 1110)
    @RequestMapping(value = "/api/sys/sql/execute", method = RequestMethod.POST)
    public ResponseData execute(
            @ApiParam(name = "sql", value = "sql语句")
            @RequestParam(value = "sql",required = false) String sql
    ) throws SQLException {
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
