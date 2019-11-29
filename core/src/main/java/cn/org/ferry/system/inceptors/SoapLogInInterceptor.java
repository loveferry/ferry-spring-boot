package cn.org.ferry.system.inceptors;

import cn.org.ferry.log.dto.LogSoap;
import cn.org.ferry.log.service.LogSoapService;
import cn.org.ferry.system.utils.NetWorkUtils;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;

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
        super(Phase.PRE_INVOKE);
    }

    @Override
    public void handleMessage(SoapMessage soapMessage) throws Fault {
        final String encoding = ifnull(soapMessage.get(ENCODING));
        String httpMethod = ifnull(soapMessage.get(HTTP_METHOD));
        String contentType = ifnull(soapMessage.get(CONTENT_TYPE));
        String protocolHeaders = ifnull(soapMessage.get(PROTOCOL_HEADERS));
        String url = NetWorkUtils.getIpAddress((HttpServletRequest)soapMessage.get(HTTP_REQUEST));
        String serviceName = ((Class<?>)soapMessage.getExchange().getService().get(ENDPOINT_CLASS)).getAnnotation(WebService.class).serviceName();
        String operation = ((QName)soapMessage.get("javax.xml.ws.wsdl.operation")).getLocalPart();
        String xml = "";
        if(null != soapMessage.getExchange().get(INPUT_CONTENT)){
            xml = soapMessage.getExchange().get(INPUT_CONTENT).toString();
        }
        LogSoap logSoap = new LogSoap(LOG_TYPE_SERVICE_IN, serviceName, operation, url, protocolHeaders, httpMethod, contentType, encoding, xml);
        logSoapService.insertLogSoap(logSoap);
        soapMessage.getExchange().put(LogSoap.class, logSoap);

        logger.debug("\n\n\nsoap service input content: \n{} \n\n\n", xml);
    }
}
