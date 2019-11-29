package cn.org.ferry.soap.service;

import cn.org.ferry.soap.dto.InHeaderMessage;
import cn.org.ferry.soap.dto.OutHeaderMessage;
import cn.org.ferry.soap.dto.PrjProject;
import cn.org.ferry.soap.dto.PrjProjectBase;

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
        name = "PrjProject",
        serviceName = "PrjProjectService",   // wsdl:service
        portName = "PrjProjectServicePort", // wsdl:port name
        endpointInterface = "cn.org.ferry.soap.service.ChinesePeopleSoapService"
)
public interface PrjProjectSoapService {

    @WebMethod(operationName = "projectReview")  // wsdl:operation
    @WebResult(name = "result")
    OutHeaderMessage<PrjProject> projectReview(@XmlElement(required = true) @WebParam(name = "baseInfo", header = true, targetNamespace = "head") InHeaderMessage inHeaderMessage,
                                               @XmlElement(required = true) @WebParam(name = "project") PrjProjectBase projectBase);
}
