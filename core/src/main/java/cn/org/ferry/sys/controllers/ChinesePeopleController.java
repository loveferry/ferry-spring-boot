package cn.org.ferry.sys.controllers;

import cn.org.ferry.sys.dto.ChinesePeople;
import cn.org.ferry.sys.service.ChinesePeopleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 中国人民信息记录(低仿) 控制器层
 */

@RestController
@RequestMapping("/api/chinese/people")
@Api(tags = "人员信息")
public class ChinesePeopleController {
    @Autowired
    private ChinesePeopleService chinesePeopleService;

    /**
     * 批量生成人员信息
     * @param size 指定生成信息数量
     */
    @ApiOperation(value = "生成人员信息", notes = "批量生成人员信息,可指定生成数量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "size", value = "生成数据量", defaultValue = "10000", paramType = "form", dataType = "Integer")
    })
    @RequestMapping(value = "/batch/generate", method = RequestMethod.POST)
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
    @ApiOperation(value = "导出人员信息", notes = "导出人员信息到excel,后缀 .xlsx")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "config", value = "导出的配置信息,格式要求为json字符串格式,里面包含字段的映射关系,字段宽度,对齐方式等", paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "当前页", defaultValue = "1", paramType = "form", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "页面大小", defaultValue = "10", paramType = "form", dataType = "Integer")
    })
    @RequestMapping(value = "/info/export", method = RequestMethod.POST)
    public void export(String config, HttpServletResponse response, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pageSize){
        chinesePeopleService.chinesePeopleExcelExport(response, config, page, pageSize);
    }

    /**
     * 查询人员信息
     * @param chinesePeople 可根据实体类属性条件查询人员信息
     * @param page 当前页
     * @param pageSize 页面大小
     */
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ApiOperation(value = "查询人员信息", notes = "根据人员信息对象查询人员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页", defaultValue = "1", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "页面大小", defaultValue = "10", paramType = "query", dataType = "Integer")
    })
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
    @ApiOperation(value = "查询人员信息", notes = "根据人员名称模糊查询人员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "名称关键字", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "当前页", defaultValue = "1", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "页面大小", defaultValue = "10", paramType = "query", dataType = "Integer")
    })
    @RequestMapping(value = "/query/name", method = RequestMethod.GET)
    public List<ChinesePeople> queryByName(String name, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pageSize){
        return chinesePeopleService.queryByName(name, page, pageSize);
    }
}
