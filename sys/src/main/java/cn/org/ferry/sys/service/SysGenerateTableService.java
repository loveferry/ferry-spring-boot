package cn.org.ferry.sys.service;

import cn.org.ferry.core.service.BaseService;
import cn.org.ferry.sys.dto.SysGenerateTable;
import cn.org.ferry.sys.exceptions.FileException;

public interface SysGenerateTableService extends BaseService<SysGenerateTable> {

    /**
     * 代码生成器
     */
    void generate(SysGenerateTable sysGenerateTable) throws FileException;
}
