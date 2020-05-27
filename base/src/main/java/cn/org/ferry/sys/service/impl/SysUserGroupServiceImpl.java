package cn.org.ferry.sys.service.impl;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import cn.org.ferry.core.service.impl.BaseServiceImpl;
import cn.org.ferry.sys.dto.SysUserGroup;
import cn.org.ferry.sys.mapper.SysUserGroupMapper;
import cn.org.ferry.sys.service.SysUserGroupService;

/**
 * Generate by code generator
 * 系统用户组表 业务接口实现类
 */

@Service
public class SysUserGroupServiceImpl extends BaseServiceImpl<SysUserGroup> implements SysUserGroupService {
	/**
	 * 日志处理对象
	 **/
	private static final Logger logger = LoggerFactory.getLogger(SysUserGroupServiceImpl.class);

	@Autowired
	private SysUserGroupMapper sysUserGroupMapper;

}