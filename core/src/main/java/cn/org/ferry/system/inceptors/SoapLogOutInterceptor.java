package cn.org.ferry.system.inceptors;

import cn.org.ferry.log.dto.LogSoap;
import cn.org.ferry.log.service.LogSoapService;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.io.CacheAndWriteOutputStream;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.io.CachedOutputStreamCallback;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.service.model.MessageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.OutputStream;
import javax.jws.WebService;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>基于 soap 协议的 web service 接口服务端发送报文日志记录拦截器
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/09/26 21:26
 */

public class SoapLogOutInterceptor extends AbstractSoapInterceptor<SoapMessage> {
    private static final Logger logger = LoggerFactory.getLogger(SoapLogOutInterceptor.class);

    @Autowired
    private LogSoapService logSoapService;

    public SoapLogOutInterceptor(){
        super(Phase.PRE_STREAM);
    }

    @Override
    public void handleMessage(SoapMessage soapMessage) throws Fault {
        HttpServletResponse response = (HttpServletResponse)soapMessage.get(HTTP_RESPONSE);
        response.setContentType(RESPONSE_CONTENT_TYPE);
        String serviceName = ((Class<?>)soapMessage.getExchange().getService().get(ENDPOINT_CLASS)).getAnnotation(WebService.class).serviceName();
        MessageInfo messageInfo = (MessageInfo) soapMessage.get(MESSAGE_INFO);
        LogSoap inputLogSoap = soapMessage.getExchange().get(LogSoap.class);
        LogSoap logSoap = new LogSoap();
        logSoap.setLogType(LOG_TYPE_SERVICE_OUT);
        logSoap.setServiceName(serviceName);
        logSoap.setOperationName(messageInfo.getOperation().getInputName());
        logSoap.setContentType(response.getContentType());
        logSoap.setEncoding(response.getCharacterEncoding());
        logSoap.setProtocolHeaders(MIME_HEADERS);
        if(inputLogSoap != null){
            logSoap.setUrl(inputLogSoap.getUrl());
            logSoap.setHttpMethod(inputLogSoap.getHttpMethod());
        }
        try {
            OutputStream os = soapMessage.getContent(OutputStream.class);
            CacheAndWriteOutputStream cwos = new CacheAndWriteOutputStream(os);
            soapMessage.setContent(OutputStream.class, cwos);
            cwos.registerCallback(new LoggingOutCallBack(logSoap));
        } catch (Exception e) {
//            logger.error("CXF记录日志错误: ", e);
            throw new Fault(e);
        }
    }

    class LoggingOutCallBack implements CachedOutputStreamCallback {
        private LogSoap logSoap;

        public LoggingOutCallBack(LogSoap logSoap){
            this.logSoap = logSoap;
        }

        @Override
        public void onClose(CachedOutputStream cos) {
            try {
                String xml = IOUtils.toString(cos.getInputStream());
                logSoap.setContent(xml);
                logSoapService.insertLogSoap(logSoap);
                logger.info("\n\n\nSoap报文:\n{}\n\n\n", xml);
            } catch (Exception e) {
                logger.error("CXF记录日志错误: ", e);
            }
        }

        @Override
        public void onFlush(CachedOutputStream cachedOutputStream) {
        }
    }
}
