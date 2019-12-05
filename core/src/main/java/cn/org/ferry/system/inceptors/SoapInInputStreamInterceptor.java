package cn.org.ferry.system.inceptors;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.phase.Phase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * <p>description
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/11/29 17:40
 */

public class SoapInInputStreamInterceptor extends AbstractSoapInterceptor<SoapMessage> {
    private static final Logger logger = LoggerFactory.getLogger(SoapLogInInterceptor.class);

    public SoapInInputStreamInterceptor(){
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
        } catch (IOException e) {
            logger.error("soap web service log in interceptor error:{}", e);
        }
    }
}
