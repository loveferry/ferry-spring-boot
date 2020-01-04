package cn.org.ferry.soap.interceptors;

import cn.org.ferry.core.utils.NetWorkUtils;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;

/**
 * <p>基于 soap 协议的 web service 接口服务端请求进入日志记录拦截器
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/09/26 15:46
 */

public class SoapInPreInvokeInterceptor extends AbstractInSoapInterceptor<SoapMessage> {
    private static final Logger logger = LoggerFactory.getLogger(SoapInPreInvokeInterceptor.class);

    public SoapInPreInvokeInterceptor(){
        // 指定拦截器触发阶段
        super(Phase.PRE_INVOKE);
    }

    @Override
    public void handleMessage(SoapMessage soapMessage) throws Fault {
        soapMessage.getExchange().put(URL, soapMessage.get(URL));
        soapMessage.getExchange().put(WSDL_OPERATION, ((QName)soapMessage.get(WSDL_OPERATION)).getLocalPart());
        soapMessage.getExchange().put(CONTENT_TYPE, soapMessage.get(CONTENT_TYPE));
        soapMessage.getExchange().put(CLIENT_ADDRESS, NetWorkUtils.getIpAddress((HttpServletRequest)soapMessage.get(HTTP_REQUEST)));

        logger.info("web service interceptor touch off pre_invoke: set some variable to soapMessage.getExchange() object");
    }
}
