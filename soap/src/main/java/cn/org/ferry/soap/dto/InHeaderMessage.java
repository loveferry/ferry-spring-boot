package cn.org.ferry.soap.dto;

import javax.xml.bind.annotation.XmlElement;

/**
 * <p>基于 soap 协议的 web service 接口的接收消息头
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/09/26 19:00
 */
public class InHeaderMessage {
    /**
     * 客户端服务名称
     */
    private String clientServerName;

    /**
     * 调用时间
     */
//    private Date invokeDate;

    @XmlElement(required = true)
    public String getClientServerName() {
        return clientServerName;
    }

    public void setClientServerName(String clientServerName) {
        this.clientServerName = clientServerName;
    }
}
