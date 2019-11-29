package cn.org.ferry.soap.service;

import cn.org.ferry.soap.dto.ChineseQueryBody;
import cn.org.ferry.soap.dto.InHeaderMessage;
import cn.org.ferry.soap.dto.OutHeaderMessage;
import cn.org.ferry.sys.dto.ChinesePeople;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;

/**
 * <p>基于 soap 协议的人员接口
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/09/25 16:49
 */

@WebService(
        name = "ChinesePeopleService",  // wsdl:portType
        serviceName = "ChinesePeopleService",   // wsdl:service
        portName = "ChinesePeopleServicePort", // wsdl:port name
        endpointInterface = "cn.org.ferry.soap.service.ChinesePeopleSoapService"
)
public interface ChinesePeopleSoapService {
    /**
     * 查询人员信息，姓名模糊查询
     * @param inHeaderMessage 接收的消息头
     * @param chineseQueryBody 接收的消息体
     * @return 返回人员信息列表
     */
    @WebMethod()  // wsdl:operation
    @WebResult(name = "result")
    OutHeaderMessage<ChinesePeople> query(@XmlElement(required = true) @WebParam(name = "baseInfo", header = true, targetNamespace = "head") InHeaderMessage inHeaderMessage,
                                          @XmlElement(required = true) @WebParam(name = "chinese") ChineseQueryBody chineseQueryBody);
}
