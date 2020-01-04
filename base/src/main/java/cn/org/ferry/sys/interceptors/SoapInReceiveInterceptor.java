package cn.org.ferry.sys.interceptors;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.phase.Phase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * <p>基于 soap 协议的 web service 接口服务端接收数据拦截器
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/11/29 17:40
 */

public class SoapInReceiveInterceptor extends AbstractInSoapInterceptor<SoapMessage> {
    private static final Logger logger = LoggerFactory.getLogger(SoapInReceiveInterceptor.class);

    public SoapInReceiveInterceptor(){
        // 指定拦截器触发阶段
        super(Phase.RECEIVE);
    }

    @Override
    public void handleMessage(SoapMessage soapMessage) throws Fault {
        try {
            String xml = copyInputStream(soapMessage, (CachedOutputStream cos) -> {
                try {
                    return IOUtils.toString(cos.getInputStream());
                } catch (IOException e) {
                    logger.error("get inputStream from CachedOutputStream error: {}", e);
                    return "";
                }
            });
            soapMessage.getExchange().put(INPUT_CONTENT, xml);
            soapMessage.getExchange().put(PROTOCOL_HEADERS, soapMessage.get(PROTOCOL_HEADERS));

            logger.info("web service interceptor touch off receive: set some variable to soapMessage.getExchange() object");

        } catch (IOException e) {
            logger.error("soap web service in receive interceptor error:{}", e);
        }
    }
}
