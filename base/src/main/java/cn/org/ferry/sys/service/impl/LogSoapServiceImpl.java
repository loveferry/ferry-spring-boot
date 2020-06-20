package cn.org.ferry.sys.service.impl;


import cn.org.ferry.core.service.impl.BaseServiceImpl;
import cn.org.ferry.sys.dto.LogSoap;
import cn.org.ferry.sys.mapper.LogSoapMapper;
import cn.org.ferry.sys.service.LogSoapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
	public void insertLogSoap(String serviceAddress, String operationName, String clientAddress, String protocolHeaders,
							  String contentType, String inputContent, String outputContent) {
		LogSoap logSoap = new LogSoap();
		logSoap.setServiceAddress(serviceAddress);
		logSoap.setOperationName(operationName);
		logSoap.setClientAddress(clientAddress);
		logSoap.setProtocolHeaders(protocolHeaders);
		logSoap.setContentType(contentType);
		logSoap.setInputContent(inputContent);
		logSoap.setOutputContent(outputContent);
		self().insert(logSoap);
		logger.info("web service log record.");
	}
}
