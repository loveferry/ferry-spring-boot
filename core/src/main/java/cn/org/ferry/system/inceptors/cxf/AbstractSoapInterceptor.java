package cn.org.ferry.system.inceptors.cxf;

import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;

/**
 * <p>基于 soap 协议的 web service 接口服务端接口抽象类
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/09/29 11:05
 */

public abstract class AbstractSoapInterceptor<E extends Message> extends AbstractPhaseInterceptor<E> {
    protected static final String EMPTY = "";
    protected static final String URL = Message.REQUEST_URL;
    protected static final String PROTOCOL_HEADERS = Message.PROTOCOL_HEADERS;
    protected static final String WSDL_OPERATION = Message.WSDL_OPERATION;
    protected static final String CONTENT_TYPE = Message.CONTENT_TYPE;
    protected static final String INPUT_CONTENT = "INPUT_CONTENT";
    protected static final String OUTPUT_CONTENT = "OUTPUT_CONTENT";
    protected static final String HTTP_REQUEST = "HTTP.REQUEST";
    protected static final String HTTP_RESPONSE = "HTTP.RESPONSE";
    protected static final String CLIENT_ADDRESS = "CLIENT_ADDRESS";

    public AbstractSoapInterceptor(String phase) {
        super(phase);
    }

    protected String ifnull(Object o){
        if(null == o){
            return EMPTY;
        }
        return String.valueOf(o);
    }
}
