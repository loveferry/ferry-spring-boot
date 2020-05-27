package cn.org.ferry.sys.service.impl;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import cn.org.ferry.core.service.impl.BaseServiceImpl;
import cn.org.ferry.sys.dto.SysUserRoleRelation;
import cn.org.ferry.sys.mapper.SysUserRoleRelationMapper;
import cn.org.ferry.sys.service.SysUserRoleRelationService;

/**
 * Generate by code generator
 * 用户权限关系表 业务接口实现类
 */

@Service
public class SysUserRoleRelationServiceImpl extends BaseServiceImpl<SysUserRoleRelation> implements SysUserRoleRelationService {
	/**
	 * 日志处理对象
	 **/
	private static final Logger logger = LoggerFactory.getLogger(SysUserRoleRelationServiceImpl.class);

	@Autowired
	private SysUserRoleRelationMapper sysUserRoleRelationMapper;

}