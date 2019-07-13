package cn.org.ferry.sys.service.impl;

import cn.org.ferry.sys.dto.SysSql;
import cn.org.ferry.sys.mapper.SysSqlMapper;
import cn.org.ferry.sys.service.SysSqlService;
import cn.org.ferry.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysSqlServiceImpl extends BaseServiceImpl<SysSql> implements SysSqlService {
    @Resource
    private SysSqlMapper sysSqlMapper;

}
