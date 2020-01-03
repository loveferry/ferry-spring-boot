package cn.org.ferry.soap.interceptors;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.io.DelegatingInputStream;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.service.model.MessageInfo;

import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.function.Function;

/**
 * <p>基于 soap 协议的 web service 接口服务端接口
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/09/29 11:05
 */

public abstract class AbstractSoapInterceptor<E extends Message> extends AbstractPhaseInterceptor<E> {
    protected static final String EMPTY = "";
    protected static final String ENCODING = Message.ENCODING;
    protected static final String HTTP_METHOD = Message.HTTP_REQUEST_METHOD;
    protected static final String CONTENT_TYPE = Message.CONTENT_TYPE;
    protected static final String RESPONSE_CONTENT_TYPE = "text/html;charset=UTF-8";
    protected static final String PROTOCOL_HEADERS = Message.PROTOCOL_HEADERS;
    protected static final String MIME_HEADERS = Message.MIME_HEADERS;
    protected static final String URL = Message.REQUEST_URL;
    protected static final String MESSAGE_INFO = MessageInfo.class.getName();
    protected static final String HTTP_RESPONSE = "HTTP.RESPONSE";
    protected static final String LOG_TYPE_SERVICE_IN = "SERVICE_IN";
    protected static final String LOG_TYPE_SERVICE_OUT = "SERVICE_OUT";

    public AbstractSoapInterceptor(String phase) {
        super(phase);
    }

    protected String ifnull(Object o){
        if(null == o){
            return EMPTY;
        }
        return String.valueOf(o);
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
}
