package cn.org.ferry.soap.service;

import cn.org.ferry.soap.dto.ConContract;
import cn.org.ferry.soap.dto.ConContractBase;
import cn.org.ferry.soap.dto.InHeaderMessage;
import cn.org.ferry.soap.dto.OutHeaderMessage;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;

/**
 * <p>基于 soap 协议的项目接口
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/11/29 09:32
 */

@WebService(
        name = "ConContract",
        serviceName = "ConContractService",   // wsdl:service
        portName = "ConContractServicePort", // wsdl:port name
        endpointInterface = "cn.org.ferry.soap.service.ConContractSoapService"
)
public interface ConContractSoapService {

    @WebMethod(operationName = "contractIncept")  // wsdl:operation
    @WebResult(name = "result")
    OutHeaderMessage<ConContract> contractIncept(@XmlElement(required = true) @WebParam(name = "baseInfo", header = true, targetNamespace = "head") InHeaderMessage inHeaderMessage,
                                                 @XmlElement(required = true) @WebParam(name = "contract") ConContractBase conContractBase);
}
