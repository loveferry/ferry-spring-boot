package cn.org.ferry.sys.service;

import cn.org.ferry.sys.dto.ChinesePeople;
import cn.org.ferry.system.service.BaseService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ChinesePeopleService extends BaseService<ChinesePeople> {
    /**
     * 批量生成人员信息
     */
    void batchGenerate(int size);

    /**
     * 导出指定页数的人员信息到excel
     * @param response 相应对象
     * @param config 导出的配置信息
     * @param page 当前页
     * @param pageSize 页面大小
     */
    void chinesePeopleExcelExport(HttpServletResponse response, String config, int page, int pageSize);

    /**
     * 可以根据实体类的属性分页查询人员信息
     */
    List<ChinesePeople> query(ChinesePeople chinesePeople, int page, int pageSize);

    /**
     * 根据 code 查询个人信息
     */
    ChinesePeople queryByCode(String code);

    /**
     * 根据 name 查询个人信息
     */
    List<ChinesePeople> queryByName(String name, int page, int pageSize);


}
