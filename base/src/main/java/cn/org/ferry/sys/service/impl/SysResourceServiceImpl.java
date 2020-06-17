package cn.org.ferry.sys.service.impl;

import cn.org.ferry.core.dto.ResponseData;
import cn.org.ferry.core.service.impl.BaseServiceImpl;
import cn.org.ferry.core.utils.ConstantUtils;
import cn.org.ferry.mybatis.enums.IfOrNot;
import cn.org.ferry.sys.dto.SysResource;
import cn.org.ferry.sys.mapper.SysResourceMapper;
import cn.org.ferry.sys.service.SysResourceService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.constraints.NotEmpty;

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

	/**
	 * 空格回车等字符的正则表达式
	 */
	private static final Pattern pattern = Pattern.compile("\\s*");

	@Autowired
	private SysResourceMapper sysResourceMapper;

	@Cacheable(value = "resource", key = "#type")
	@Override
	public List<String> queryAllEnabledResourceByType(String type) {
		logger.info("query all enabled rest resource.");
		return sysResourceMapper.queryAllEnabledResourceByType(type);
	}

	@CacheEvict(value = "resource", key = "#type")
	@Override
	public void expireAllEnabledResourceByType(String type) {
		logger.info("expire cache of resource:{}", type);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void scriptRegister(@NotEmpty(message = "脚本内容不能为空！") String scripts) {
		logger.info("script register start.");
		Assert.hasText(scripts, "脚本内容不能为空");
		scripts = pattern.matcher(scripts).replaceAll(ConstantUtils.StringConstant.EMPTY);
		String semicolon = ConstantUtils.StringConstant.SEMICOLON;
		Assert.isTrue(StringUtils.endsWith(scripts, semicolon), "脚本必须以" + semicolon + "结尾");
		String[] scriptsArray = StringUtils.split(scripts, semicolon);
		for (String script : scriptsArray) {
			if(StringUtils.isEmpty(script)){
				continue;
			}
			String scriptType = StringUtils.substringBefore(script, ConstantUtils.StringConstant.LEFT_PARENTHESIS);
			switch (scriptType){
				case ConstantUtils.ScriptType.RESOURCE_DEFINITION:      // 资源定义
					scriptDefinition(script);
					break;
				case ConstantUtils.ScriptType.RESOURCE_ALLOCATION:      // 资源分配
					break;
				case ConstantUtils.ScriptType.AUTHORITY_DEFINITION:     // 权限定义
					break;
				case ConstantUtils.ScriptType.AUTHORITY_ALLOCATION:     // 权限分配
					break;
				default:
					Assert.isTrue(true, "脚本类型【"+scriptType+"】错误");
			}
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void scriptDefinition(String script) {
		logger.info("resource script definition start.");
		Assert.hasText(script, "脚本内容不能为空");
		Assert.isTrue(
				StringUtils.startsWith(script, ConstantUtils.ScriptType.RESOURCE_DEFINITION+ConstantUtils.StringConstant.LEFT_PARENTHESIS) &&
						StringUtils.endsWith(script, ConstantUtils.StringConstant.RIGHT_PARENTHESIS),
				"资源定义脚本语句【"+script+"】不合法：脚本形式如 resource_definition(type,path,description,enabled_flag) 。"
		);
		String container = StringUtils.substringBetween(
				script,
				ConstantUtils.StringConstant.LEFT_PARENTHESIS,
				ConstantUtils.StringConstant.RIGHT_PARENTHESIS
		);
		String[] parts = StringUtils.split(container, ConstantUtils.StringConstant.COMMA);
		Assert.isTrue(
				ArrayUtils.isNotEmpty(parts) && parts.length == 4,
				"资源定义脚本语句【"+script+"】不合法：脚本形式如 resource_definition(type,path,description,enabled_flag) 。"
		);
		String type = parts[0];
		String path = parts[1];
		String description = parts[2];
		String enabledFlag = parts[3];
		Assert.hasText(type, "资源定义脚本语句【"+script+"】资源类型不能为空");
		Assert.hasText(path, "资源定义脚本语句【"+script+"】资源路径不能为空");
		final String[] yAndN = new String[]{"Y", "N"};
		Assert.isTrue(StringUtils.equalsAny(enabledFlag, yAndN), "资源定义脚本语句【"+script+"】是否启用枚举值为：Y,N");
		// 插入表
		SysResource sysResource = new SysResource();
		sysResource.setResourceType(type);
		sysResource.setResourcePath(path);
		sysResource = sysResourceMapper.selectOne(sysResource);
		if(Objects.isNull(sysResource)){
			sysResource = new SysResource();
			sysResource.setResourceType(type);
			sysResource.setResourcePath(path);
			sysResource.setDescription(description);
			sysResource.setEnabledFlag(IfOrNot.valueOf(enabledFlag));
			insertSelective(sysResource);
			logger.info("resource definition success.");
		}else{
			sysResource.setEnabledFlag(IfOrNot.valueOf(enabledFlag));
			sysResource.setDescription(description);
			int i = updateByPrimaryKey(sysResource);
			logger.info("resource update {} record.", i);
		}
		// 将缓存中的资源
		expireAllEnabledResourceByType(type);
	}
}
