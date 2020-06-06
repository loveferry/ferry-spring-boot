package cn.org.ferry.sys.service.impl;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import cn.org.ferry.core.service.impl.BaseServiceImpl;
import cn.org.ferry.sys.dto.SysAuthorityResourceRelation;
import cn.org.ferry.sys.mapper.SysAuthorityResourceRelationMapper;
import cn.org.ferry.sys.service.SysAuthorityResourceRelationService;

/**
 * Generate by code generator
 * 权限资源关系表 业务接口实现类
 */

@Service
public class SysAuthorityResourceRelationServiceImpl extends BaseServiceImpl<SysAuthorityResourceRelation> implements SysAuthorityResourceRelationService {
	/**
	 * 日志处理对象
	 **/
	private static final Logger logger = LoggerFactory.getLogger(SysAuthorityResourceRelationServiceImpl.class);

	@Autowired
	private SysAuthorityResourceRelationMapper sysAuthorityResourceRelationMapper;

}