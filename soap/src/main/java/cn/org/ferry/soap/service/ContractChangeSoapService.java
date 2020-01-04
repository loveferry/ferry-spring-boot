package cn.org.ferry.soap.service;

import cn.org.ferry.soap.dto.ContractChange;
import cn.org.ferry.soap.dto.InHeaderMessage;
import cn.org.ferry.soap.dto.OutHeaderMessage;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.ws.RequestWrapper;

/**
 * <p>description
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/12/11 11:48
 */

@WebService(
        name = "ContractChange",
        serviceName = "ContractChangeService",   // wsdl:service
        portName = "ContractChangeServicePort", // wsdl:port name
        endpointInterface = "cn.org.ferry.soap.service.ContractChangeSoapService",
        targetNamespace = "http://lovesy.org.cn:50318"
)
public interface ContractChangeSoapService  {

    @WebMethod(operationName = "contractChange", action = "contractChange")  // wsdl:operation
    @WebResult(name = "result")
    @RequestWrapper(localName = "PROJECT_LIST", targetNamespace = "prj")
    OutHeaderMessage<ContractChange> contractChange(@XmlElement(required = true) @WebParam(name = "baseInfo", header = true, targetNamespace = "head") InHeaderMessage inHeaderMessage,
                                                    @XmlElement(required = true) @WebParam(name = "PROJECT") List<ContractChange> contractChangeList);
}
