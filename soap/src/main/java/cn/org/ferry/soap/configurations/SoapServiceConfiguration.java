package cn.org.ferry.soap.configurations;

import cn.org.ferry.soap.service.ConContractSoapService;
import cn.org.ferry.soap.service.ContractChangeSoapService;
import cn.org.ferry.soap.service.EveryDayPlanSoapService;
import cn.org.ferry.soap.service.PrjProjectSoapService;
import cn.org.ferry.sys.interceptors.SoapInPreInvokeInterceptor;
import cn.org.ferry.sys.interceptors.SoapInReceiveInterceptor;
import cn.org.ferry.sys.interceptors.SoapOutPreStreamInterceptor;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

/**
 * <p>Spring-WS 构建 WebService 服务的配置类
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/09/25 18:46
 */

@Configuration
@ConditionalOnClass({SpringBus.class, CXFServlet.class})
public class SoapServiceConfiguration {
    @Autowired
    private SpringBus springBus;
    @Autowired
    private SoapInReceiveInterceptor soapInReceiveInterceptor;
    @Autowired
    private SoapInPreInvokeInterceptor soapInPreInvokeInterceptor;
    @Autowired
    private SoapOutPreStreamInterceptor soapOutPreStreamInterceptor;

    private void addInterceptors(EndpointImpl endpoint){
        endpoint.getInInterceptors().add(soapInReceiveInterceptor);
        endpoint.getInInterceptors().add(soapInPreInvokeInterceptor);
        endpoint.getOutInterceptors().add(soapOutPreStreamInterceptor);
    }

    @Autowired
    @Bean(name = "project")
    public Endpoint projectEndPoint(PrjProjectSoapService prjProjectSoapService){
        EndpointImpl endpoint = new EndpointImpl(springBus, prjProjectSoapService);

        /*Map<String, Object> propertyMap = new HashMap<>();

        Map<String, Object> nsMap = new HashMap<>();
        nsMap.put("ferry", "http://lovesy.org.cn:50318");
        nsMap.put("env", "http://www.w3.org/2003/05/soap-envelope");

        propertyMap.put("soap.env.ns.map", nsMap);
        propertyMap.put("disable.outputstream.optimization", true);
        endpoint.setProperties(propertyMap);*/


        endpoint.publish("/project");

        addInterceptors(endpoint);
//        map.put("soap.force.doclit.bare", true);

        return endpoint;
    }

    @Autowired
    @Bean(name = "contract")
    public Endpoint contractEndPoint(ConContractSoapService conContractSoapService){
        EndpointImpl endpoint = new EndpointImpl(springBus, conContractSoapService);
        endpoint.publish("/contract");
        addInterceptors(endpoint);
        return endpoint;
    }

    @Autowired
    @Bean(name = "everyDayPlan")
    public Endpoint everyDayPlanEndPoint(EveryDayPlanSoapService everyDayPlanSoapService){
        EndpointImpl endpoint = new EndpointImpl(springBus, everyDayPlanSoapService);
        endpoint.publish("/everyDayPlan");
        addInterceptors(endpoint);
        return endpoint;
    }

    @Autowired
    @Bean(name = "contractChange")
    public Endpoint contractChangePoint(ContractChangeSoapService contractChangeSoapService){
        EndpointImpl endpoint = new EndpointImpl(springBus, contractChangeSoapService);
        endpoint.publish("/contractChange");
        addInterceptors(endpoint);
        return endpoint;
    }


}
