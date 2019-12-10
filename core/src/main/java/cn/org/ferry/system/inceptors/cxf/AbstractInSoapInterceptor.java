package cn.org.ferry.system.inceptors.cxf;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.io.DelegatingInputStream;
import org.apache.cxf.message.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.function.Function;

/**
 * <p>基于 soap 协议的 web service 接口服务端接口流入拦截器链抽象类
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/09/29 11:05
 */

public abstract class AbstractInSoapInterceptor<E extends Message> extends AbstractSoapInterceptor<E> {
    public AbstractInSoapInterceptor(String phase) {
        super(phase);
    }

    protected String copyInputStream(E e, Function<CachedOutputStream, String> f) throws IOException {
        InputStream is = e.getContent(InputStream.class);
        InputStream dis = is instanceof DelegatingInputStream ? ((DelegatingInputStream)is).getInputStream() : is;
        CachedOutputStream cos = new CachedOutputStream();
        IOUtils.copy(dis, cos);
        cos.flush();
        InputStream sis = new SequenceInputStream(cos.getInputStream(), dis);
        if (is instanceof DelegatingInputStream) {
            ((DelegatingInputStream)is).setInputStream(sis);
        } else {
            e.setContent(InputStream.class, sis);
        }
        String xml = f.apply(cos);
        cos.close();
        return xml;
    }

    /**
     *     阶段名称	                            阶段功能描述
     *     RECEIVE	                            Transport level processing(接收阶段，传输层处理)
     *     (PRE/USER/POST)_STREAM	            Stream level processing/transformations(流处理/转换阶段)
     *     READ	                                This is where header reading typically occurs(SOAPHeader读取)
     *     (PRE/USER/POST)_PROTOCOL	            Protocol processing, such as JAX-WS SOAP handlers(协议处理阶段，例如JAX-WS的Handler处理)
     *     UNMARSHAL	                        Unmarshalling of the request(SOAP请求解码阶段)
     *     (PRE/USER/POST)_LOGICAL	            Processing of the umarshalled request(SOAP请求解码处理阶段)
     *     PRE_INVOKE	                        Pre invocation actions(调用业务处理之前进入该阶段)
     *     INVOKE	                            Invocation of the service(调用业务阶段)
     *     POST_INVOKE	                        Invocation of the outgoing chain if there is one(提交业务处理结果，并触发输入连接器)
     */

}
