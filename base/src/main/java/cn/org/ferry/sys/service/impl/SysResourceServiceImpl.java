package cn.org.ferry.sys.service.impl;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import cn.org.ferry.core.service.impl.BaseServiceImpl;
import cn.org.ferry.sys.dto.SysResource;
import cn.org.ferry.sys.mapper.SysResourceMapper;
import cn.org.ferry.sys.service.SysResourceService;

/**
 * Generate by code generator
 * 系统资源表 业务接口实现类
 */

@Service
public class SysResourceServiceImpl extends BaseServiceImpl<SysResource> implements SysResourceService {
	/**
	 * 日志处理对象
	 **/
	private static final Logger logger = LoggerFactory.getLogger(SysResourceServiceImpl.class);

	@Autowired
	private SysResourceMapper sysResourceMapper;

}