package cn.org.ferry.sys.service.impl;

import cn.org.ferry.core.service.impl.BaseServiceImpl;
import cn.org.ferry.sys.dto.SysRole;
import cn.org.ferry.sys.mapper.SysRoleMapper;
import cn.org.ferry.sys.service.SysRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Generate by code generator
 * 系统角色表 业务接口实现类
 */

@Service
@Transactional
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole> implements SysRoleService {
	/**
	 * 日志处理对象
	 **/
	private static final Logger logger = LoggerFactory.getLogger(SysRoleServiceImpl.class);

	@Autowired
	private SysRoleMapper sysRoleMapper;

	@Cacheable(value = "role", key = "#pattern")
	@Override
	public List<String> obtainEnabledRolesByPattern(String pattern) {
		logger.info("obtain enabled roles by pattern: {}", pattern);
		return sysRoleMapper.obtainEnabledRolesByPattern(pattern);
	}

	@CacheEvict(value = "role", key = "#pattern")
	@Override
	public void expireEnabledRolesByPattern(String pattern) {
		logger.info("Clean the role of pattern cache {}.", pattern);
	}

	@Override
	public List<String> queryAllEnabledRoleCode() {
		return sysRoleMapper.queryAllEnabledRoleCode();
	}
}
