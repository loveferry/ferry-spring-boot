package cn.org.ferry.sys.controllers;

import cn.org.ferry.sys.dto.ChinesePeople;
import cn.org.ferry.sys.service.ChinesePeopleService;
import cn.org.ferry.system.annotation.LoginPass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

/**
 * 中国人民信息记录(低仿) 控制器层
 */

@RestController
@RequestMapping("/api/chinese/people")
@LoginPass
public class ChinesePeopleController {
    @Autowired
    private ChinesePeopleService chinesePeopleService;

    /**
     * 批量生成人员信息
     * @param size 指定生成信息数量
     */
    @RequestMapping("/batch/generate")
    @ResponseBody
    public void batchGenerate(@RequestParam(defaultValue = "10000")int size, HttpServletRequest httpServletRequest) throws SQLException{
        chinesePeopleService.batchGenerate(size);
    }

    /**
     * 导出人员信息到excel,后缀 .xlsx
     * @param config 导出的参数
     * @param response 相应对象
     * @param page 当前页
     * @param pageSize 页面大小
     */
    @RequestMapping("/info/export")
    @ResponseBody
    public void export(String config, HttpServletResponse response, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pageSize){
        chinesePeopleService.chinesePeopleExcelExport(response, config, page, pageSize);
    }

    /**
     * 查询人员信息
     * @param chinesePeople 可根据实体类属性条件查询人员信息
     * @param page 当前页
     * @param pageSize 页面大小
     */
    @RequestMapping("/query")
    @ResponseBody
    public List<ChinesePeople> query(ChinesePeople chinesePeople, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pageSize){
        return chinesePeopleService.query(chinesePeople, page, pageSize);
    }

    /**
     * 根据信息模糊查询人员信息,查询结果以键值对形式存储在redis缓存中
     * @param name 姓名
     * @param page 当前页
     * @param pageSize 页面大小
     * @return 返回相匹配的所有人员信息
     */
    @RequestMapping("/query/name")
    @ResponseBody
    public List<ChinesePeople> queryByName(String name, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pageSize){
        return chinesePeopleService.queryByName(name, page, pageSize);
    }
}
