package cn.org.ferry.system.config;

import cn.org.ferry.soap.service.ChinesePeopleSoapService;
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
        endpoint.getInInterceptors().add(soapLogInInterceptor());
        endpoint.getOutInterceptors().add(soapLogOutInterceptor());
        return endpoint;
    }
}
