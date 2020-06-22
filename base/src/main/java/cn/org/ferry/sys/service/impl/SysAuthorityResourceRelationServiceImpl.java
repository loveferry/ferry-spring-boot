package cn.org.ferry.sys.service.impl;

import cn.org.ferry.core.service.impl.BaseServiceImpl;
import cn.org.ferry.sys.dto.SysAuthorityResourceRelation;
import cn.org.ferry.sys.mapper.SysAuthorityResourceRelationMapper;
import cn.org.ferry.sys.service.SysAuthorityResourceRelationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Generate by code generator
 * 权限资源关系表 业务接口实现类
 */

@Service
@Transactional
public class SysAuthorityResourceRelationServiceImpl extends BaseServiceImpl<SysAuthorityResourceRelation> implements SysAuthorityResourceRelationService {
	/**
	 * 日志处理对象
	 **/
	private static final Logger logger = LoggerFactory.getLogger(SysAuthorityResourceRelationServiceImpl.class);

	@Autowired
	private SysAuthorityResourceRelationMapper sysAuthorityResourceRelationMapper;

}
