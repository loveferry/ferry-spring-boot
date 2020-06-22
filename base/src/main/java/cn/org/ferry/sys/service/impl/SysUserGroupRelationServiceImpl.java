package cn.org.ferry.sys.service.impl;

import cn.org.ferry.core.service.impl.BaseServiceImpl;
import cn.org.ferry.sys.dto.SysUserGroupRelation;
import cn.org.ferry.sys.mapper.SysUserGroupRelationMapper;
import cn.org.ferry.sys.service.SysUserGroupRelationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Generate by code generator
 * 用户和用户组关系表 业务接口实现类
 */

@Service
@Transactional
public class SysUserGroupRelationServiceImpl extends BaseServiceImpl<SysUserGroupRelation> implements SysUserGroupRelationService {
	/**
	 * 日志处理对象
	 **/
	private static final Logger logger = LoggerFactory.getLogger(SysUserGroupRelationServiceImpl.class);

	@Autowired
	private SysUserGroupRelationMapper sysUserGroupRelationMapper;

}
