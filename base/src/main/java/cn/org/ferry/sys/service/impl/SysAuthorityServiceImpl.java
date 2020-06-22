package cn.org.ferry.sys.service.impl;

import cn.org.ferry.core.service.impl.BaseServiceImpl;
import cn.org.ferry.sys.dto.SysAuthority;
import cn.org.ferry.sys.mapper.SysAuthorityMapper;
import cn.org.ferry.sys.service.SysAuthorityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Generate by code generator
 * 系统权限表 业务接口实现类
 */

@Service
@Transactional
public class SysAuthorityServiceImpl extends BaseServiceImpl<SysAuthority> implements SysAuthorityService {
	/**
	 * 日志处理对象
	 **/
	private static final Logger logger = LoggerFactory.getLogger(SysAuthorityServiceImpl.class);

	@Autowired
	private SysAuthorityMapper sysAuthorityMapper;

}
