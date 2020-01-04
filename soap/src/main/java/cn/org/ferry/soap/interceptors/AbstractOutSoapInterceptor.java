package cn.org.ferry.soap.interceptors;

import org.apache.cxf.message.Message;

/**
 * <p>基于 soap 协议的 web service 接口服务端接口流出拦截器链抽象类
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/09/29 11:05
 */

public abstract class AbstractOutSoapInterceptor<E extends Message> extends AbstractSoapInterceptor<E> {

    public AbstractOutSoapInterceptor(String phase) {
        super(phase);
    }

    /**
     *     阶段名称	                    阶段功能描述
     *     SETUP	                    Any set up for the following phases(设置阶段)
     *     (PRE/USER/POST)_LOGICAL	    Processing of objects about to marshalled
     *     PREPARE_SEND	                Opening of the connection(消息发送准备阶段，在该阶段创建Connection)
     *     PRE_STREAM	                流准备阶段
     *     PRE_PROTOCOL	                Misc protocol actions(协议准备阶段)
     *     WRITE	                    Writing of the protocol message, such as the SOAP Envelope.(写消息阶段)
     *     MARSHAL	                    Marshalling of the objects
     *     (PRE/USER/POST)_PROTOCOL     Processing of the protocol message
     *     (PRE/USER/POST)_STREAM	    Processing of the byte level message(字节处理阶段，在该阶段把消息转为字节)
     *     SEND	                        消息发送
     */

}
