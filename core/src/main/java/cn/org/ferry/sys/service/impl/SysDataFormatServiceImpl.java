package cn.org.ferry.sys.service.impl;

import cn.org.ferry.sys.dto.SysDataFormat;
import cn.org.ferry.sys.mapper.SysDataFormatMapper;
import cn.org.ferry.sys.service.SysDataFormatService;
import cn.org.ferry.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysDataFormatServiceImpl extends BaseServiceImpl<SysDataFormat> implements SysDataFormatService {
    @Resource
    private SysDataFormatMapper sysDataFormatMapper;

}
