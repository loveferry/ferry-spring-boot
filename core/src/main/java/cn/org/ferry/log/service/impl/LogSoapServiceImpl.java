package cn.org.ferry.log.service.impl;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import cn.org.ferry.system.service.impl.BaseServiceImpl;
import cn.org.ferry.log.dto.LogSoap;
import cn.org.ferry.log.mapper.LogSoapMapper;
import cn.org.ferry.log.service.LogSoapService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Generate by code generator
 * soap接口日志记录表 业务接口实现类
 */

@Service
public class LogSoapServiceImpl extends BaseServiceImpl<LogSoap> implements LogSoapService {
	/**
	 * 日志处理对象
	 **/
	private static final Logger logger = LoggerFactory.getLogger(LogSoapServiceImpl.class);

	@Autowired
	private LogSoapMapper logSoapMapper;

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void insertLogSoap(LogSoap logSoap) {
		insert(logSoap);
	}
}
