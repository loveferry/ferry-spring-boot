package cn.org.ferry.soap.interceptors;

import cn.org.ferry.soap.dto.LogSoap;
import cn.org.ferry.soap.service.LogSoapService;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.phase.Phase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import javax.jws.WebService;

/**
 * <p>基于 soap 协议的 web service 接口服务端请求进入日志记录拦截器
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/09/26 15:46
 */

public class SoapLogInInterceptor extends AbstractSoapInterceptor<SoapMessage> {
    private static final Logger logger = LoggerFactory.getLogger(SoapLogInInterceptor.class);

    @Autowired
    private LogSoapService logSoapService;

    public SoapLogInInterceptor(){
        // 指定拦截器触发阶段
        super(Phase.RECEIVE);
    }

    @Override
    public void handleMessage(SoapMessage soapMessage) throws Fault {
        final String encoding = ifnull(soapMessage.get(ENCODING));
        String httpMethod = ifnull(soapMessage.get(HTTP_METHOD));
        String contentType = ifnull(soapMessage.get(CONTENT_TYPE));
        String protocolHeaders = ifnull(soapMessage.get(PROTOCOL_HEADERS));
        String url = ifnull(soapMessage.get(URL));
        String serviceName = ((Class<?>)soapMessage.getExchange().getService().get("endpoint.class")).getAnnotation(WebService.class).serviceName();
        try {
            String xml = copyInputStream(soapMessage, (CachedOutputStream cos) -> {
                try {
                    return IOUtils.toString(cos.getInputStream());
                } catch (IOException e) {
                    logger.error("get inputStream from CachedOutputStream error: {}", e);
                    return "";
                }
            });
            logger.debug("wsdl content: \n{}", xml);
            LogSoap logSoap = new LogSoap(LOG_TYPE_SERVICE_IN, serviceName, serviceName, url, protocolHeaders, httpMethod, contentType, encoding, xml);
            logSoapService.insertLogSoap(logSoap);
            soapMessage.getExchange().put(LogSoap.class, logSoap);
        } catch (IOException e) {
            logger.error("soap web service log in interceptor error:{}", e);
        }
    }
}
