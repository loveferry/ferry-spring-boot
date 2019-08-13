package cn.org.ferry.sys.service;

import cn.org.ferry.sys.dto.SysGenerateTable;
import cn.org.ferry.system.service.BaseService;

import java.io.IOException;

public interface SysGenerateTableService extends BaseService<SysGenerateTable> {

    /**
     * 代码生成器
     */
    void generate(SysGenerateTable sysGenerateTable) throws IOException;
}
