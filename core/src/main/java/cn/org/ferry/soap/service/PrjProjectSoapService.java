package cn.org.ferry.soap.service;

import cn.org.ferry.soap.dto.InHeaderMessage;
import cn.org.ferry.soap.dto.OutHeaderMessage;
import cn.org.ferry.soap.dto.PrjProject;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.ws.RequestWrapper;

/**
 * <p>基于 soap 协议的签约投放接口
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/11/29 09:32
 */

@WebService(
        name = "PrjProject",
        serviceName = "PrjProjectService",   // wsdl:service
        portName = "PrjProjectServicePort", // wsdl:port name
        endpointInterface = "cn.org.ferry.soap.service.PrjProjectSoapService",
        targetNamespace = "http://lovesy.org.cn:50318"
)
public interface PrjProjectSoapService {

    @WebMethod(operationName = "projectReview", action = "projectReview")  // wsdl:operation
    @WebResult(name = "result", targetNamespace = "prj")
    @RequestWrapper(localName = "PROJECT_LIST", targetNamespace = "prj")
//    @ResponseWrapper(localName = "RESULT", targetNamespace = "prj")
    OutHeaderMessage<PrjProject> projectReview(@XmlElement(required = true) @WebParam(name = "baseInfo", header = true, targetNamespace = "head") InHeaderMessage inHeaderMessage,
                                               @XmlElement(required = true) @WebParam(name = "PROJECT") List<PrjProject> prjProjectList);
}
