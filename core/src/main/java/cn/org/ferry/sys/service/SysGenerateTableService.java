package cn.org.ferry.sys.service;

import cn.org.ferry.sys.dto.SysGenerateTable;
import cn.org.ferry.system.exception.FileException;
import cn.org.ferry.system.service.BaseService;

public interface SysGenerateTableService extends BaseService<SysGenerateTable> {

    /**
     * 代码生成器
     */
    void generate(SysGenerateTable sysGenerateTable) throws FileException;
}
