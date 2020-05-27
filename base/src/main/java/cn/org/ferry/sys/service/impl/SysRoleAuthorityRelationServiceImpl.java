package cn.org.ferry.sys.service.impl;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import cn.org.ferry.core.service.impl.BaseServiceImpl;
import cn.org.ferry.sys.dto.SysRoleAuthorityRelation;
import cn.org.ferry.sys.mapper.SysRoleAuthorityRelationMapper;
import cn.org.ferry.sys.service.SysRoleAuthorityRelationService;

/**
 * Generate by code generator
 * 系统角色权限关系表 业务接口实现类
 */

@Service
public class SysRoleAuthorityRelationServiceImpl extends BaseServiceImpl<SysRoleAuthorityRelation> implements SysRoleAuthorityRelationService {
	/**
	 * 日志处理对象
	 **/
	private static final Logger logger = LoggerFactory.getLogger(SysRoleAuthorityRelationServiceImpl.class);

	@Autowired
	private SysRoleAuthorityRelationMapper sysRoleAuthorityRelationMapper;

}