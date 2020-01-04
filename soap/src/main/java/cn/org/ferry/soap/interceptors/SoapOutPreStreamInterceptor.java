package cn.org.ferry.soap.interceptors;

import cn.org.ferry.soap.service.LogSoapService;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.io.CacheAndWriteOutputStream;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.io.CachedOutputStreamCallback;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.phase.Phase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>基于 soap 协议的 web service 接口服务端流出输出流处理前的拦截器
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/12/10 09:58
 */

public class SoapOutPreStreamInterceptor extends AbstractOutSoapInterceptor<SoapMessage> {
    private static final Logger logger = LoggerFactory.getLogger(SoapOutPreStreamInterceptor.class);

    @Autowired
    private LogSoapService logSoapService;

    public SoapOutPreStreamInterceptor(){
        super(Phase.PRE_STREAM);
    }

    @Override
    public void handleMessage(SoapMessage soapMessage) throws Fault {
        Exchange exchange = soapMessage.getExchange();

        HttpServletResponse response = (HttpServletResponse)soapMessage.get(HTTP_RESPONSE);
        response.setContentType((String)exchange.get(CONTENT_TYPE));
        try {
            OutputStream os = soapMessage.getContent(OutputStream.class);
            CacheAndWriteOutputStream cwos = new CacheAndWriteOutputStream(os);
            soapMessage.setContent(OutputStream.class, cwos);
            cwos.registerCallback(new ReadCallBack(exchange));

            logger.info("web service interceptor touch off pre_stream: set some variable to soapMessage.getExchange() object and record log");

        } catch (Exception e) {
            throw new Fault(e);
        }
    }

    class ReadCallBack implements CachedOutputStreamCallback {
        private Exchange exchange;

        public ReadCallBack(Exchange exchange){
            this.exchange = exchange;
        }

        @Override
        public void onClose(CachedOutputStream cos) {
            try {
                String xml = IOUtils.toString(cos.getInputStream());
                exchange.put(OUTPUT_CONTENT, xml);
                logSoapService.insertLogSoap(ifnull(exchange.get(URL)), ifnull(exchange.get(WSDL_OPERATION)), ifnull(exchange.get(CLIENT_ADDRESS)),
                        ifnull(exchange.get(PROTOCOL_HEADERS)), ifnull(exchange.get(CONTENT_TYPE)), ifnull(exchange.get(INPUT_CONTENT)),
                        ifnull(exchange.get(OUTPUT_CONTENT)));
            } catch (Exception e) {
                logger.error("CXF拦截器输出流复制错误: ", e);
            }
        }

        @Override
        public void onFlush(CachedOutputStream cos) {
        }
    }
}
