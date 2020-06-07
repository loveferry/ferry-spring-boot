package cn.org.ferry.sys.service.impl;

import cn.org.ferry.core.service.impl.BaseServiceImpl;
import cn.org.ferry.sys.dto.SysDataFormat;
import cn.org.ferry.sys.mapper.SysDataFormatMapper;
import cn.org.ferry.sys.service.SysDataFormatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysDataFormatServiceImpl extends BaseServiceImpl<SysDataFormat> implements SysDataFormatService {
    @Autowired
    private SysDataFormatMapper sysDataFormatMapper;

}
