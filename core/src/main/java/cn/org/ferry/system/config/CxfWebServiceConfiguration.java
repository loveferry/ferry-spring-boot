package cn.org.ferry.system.config;

import cn.org.ferry.soap.service.ChinesePeopleSoapService;
import cn.org.ferry.soap.service.ConContractSoapService;
import cn.org.ferry.soap.service.EveryDayPlanSoapService;
import cn.org.ferry.soap.service.PrjProjectSoapService;
import cn.org.ferry.system.inceptors.SoapInInputStreamInterceptor;
import cn.org.ferry.system.inceptors.SoapLogInInterceptor;
import cn.org.ferry.system.inceptors.SoapLogOutInterceptor;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;
import javax.xml.ws.Endpoint;

/**
 * <p>Spring-WS 构建 WebService 服务的配置类
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/09/25 18:46
 */
@Configuration
@ConditionalOnClass({SpringBus.class, CXFServlet.class})
public class CxfWebServiceConfiguration {
    @Value("${ferry.cxf.path}")
    private String path;
    @Autowired
    private SpringBus springBus;

    /**
     * cxf servlet 名称指定为 cxfServletRegistration，否则 spring 会生成两个 servlet
     * @see  org.apache.cxf.spring.boot.autoconfigure.CxfAutoConfiguration
     */
    @Bean(name = "cxfServletRegistration")
    public ServletRegistrationBean<CXFServlet> cxfServletRegistration() {
        Objects.requireNonNull(path, "cxf path can not empty");
        return new ServletRegistrationBean(new CXFServlet(), new String[]{path.endsWith("/") ? path + "*" : path + "/*"});
    }

    /**
     * soap 接口服务端流入拦截器
     */
    @Bean
    public SoapInInputStreamInterceptor soapInInputStreamInterceptor(){
        return new SoapInInputStreamInterceptor();
    }

    /**
     * soap接口服务端流入日志拦截器
     */
    @Bean
    public SoapLogInInterceptor soapLogInInterceptor(){
        return new SoapLogInInterceptor();
    }

    /**
     * soap接口服务端流出日志拦截器
     */
    @Bean
    public SoapLogOutInterceptor soapLogOutInterceptor(){
        return new SoapLogOutInterceptor();
    }

    @Autowired
    @Bean(name = "chinese")
    public Endpoint chinesePeopleEndPoint(ChinesePeopleSoapService chinesePeopleSoapService){
        EndpointImpl endpoint = new EndpointImpl(springBus, chinesePeopleSoapService);
        endpoint.publish("/chinese");
        endpoint.getInInterceptors().add(soapInInputStreamInterceptor());
        endpoint.getInInterceptors().add(soapLogInInterceptor());
        endpoint.getOutInterceptors().add(soapLogOutInterceptor());
        return endpoint;
    }

    @Autowired
    @Bean(name = "project")
    public Endpoint projectEndPoint(PrjProjectSoapService prjProjectSoapService){
        EndpointImpl endpoint = new EndpointImpl(springBus, prjProjectSoapService);
        endpoint.publish("/project");
        endpoint.getInInterceptors().add(soapInInputStreamInterceptor());
        endpoint.getInInterceptors().add(soapLogInInterceptor());
        endpoint.getOutInterceptors().add(soapLogOutInterceptor());
        return endpoint;
    }

    @Autowired
    @Bean(name = "contract")
    public Endpoint contractEndPoint(ConContractSoapService conContractSoapService){
        EndpointImpl endpoint = new EndpointImpl(springBus, conContractSoapService);
        endpoint.publish("/contract");
        endpoint.getInInterceptors().add(soapInInputStreamInterceptor());
        endpoint.getInInterceptors().add(soapLogInInterceptor());
        endpoint.getOutInterceptors().add(soapLogOutInterceptor());
        return endpoint;
    }

    @Autowired
    @Bean(name = "everyDayPlan")
    public Endpoint everyDayPlanEndPoint(EveryDayPlanSoapService everyDayPlanSoapService){
        EndpointImpl endpoint = new EndpointImpl(springBus, everyDayPlanSoapService);
        endpoint.publish("/everyDayPlan");
        endpoint.getInInterceptors().add(soapInInputStreamInterceptor());
        endpoint.getInInterceptors().add(soapLogInInterceptor());
        endpoint.getOutInterceptors().add(soapLogOutInterceptor());
        return endpoint;
    }
}
