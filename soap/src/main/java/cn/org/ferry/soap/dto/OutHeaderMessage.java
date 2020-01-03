package cn.org.ferry.soap.dto;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>基于 soap 协议的 web service 接口的发送消息头
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/09/26 19:00
 */
@XmlType(propOrder = {"serverName", "status", "list"})
@XmlAccessorType(XmlAccessType.FIELD)
public class OutHeaderMessage<T>{
    /**
     * 客户端服务名称
     */
    private String serverName;

    /**
     * 调用时间
     */
//    private Date responseDate;

    /**
     * 状态
     */
    private String status;

    /**
     * 接口返回详细信息
     */
    @XmlElementWrapper(name = "list")
    @XmlAnyElement
    private List<T> list;

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
