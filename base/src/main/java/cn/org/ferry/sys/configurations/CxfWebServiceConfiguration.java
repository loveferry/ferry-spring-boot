package cn.org.ferry.sys.configurations;

import cn.org.ferry.sys.interceptors.SoapInPreInvokeInterceptor;
import cn.org.ferry.sys.interceptors.SoapInReceiveInterceptor;
import cn.org.ferry.sys.interceptors.SoapOutPreStreamInterceptor;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Objects;

/**
 * <p>Spring-WS 构建 WebService 服务的配置类
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/09/25 18:46
 */
@Configuration
@ConditionalOnClass({SpringBus.class, CXFServlet.class})
@PropertySource(value = {"classpath:config_base.properties"})
public class CxfWebServiceConfiguration {
    @Value("${cxf.path}")
    private String path;

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
     * soap 接口服务端流入输接收数据处理拦截器
     */
    @Bean
    public SoapInReceiveInterceptor soapInReceiveInterceptor(){
        return new SoapInReceiveInterceptor();
    }

    /**
     * soap接口服务端流入业务方法处理前拦截器
     */
    @Bean
    public SoapInPreInvokeInterceptor soapInPreInvokeInterceptor(){
        return new SoapInPreInvokeInterceptor();
    }

    /**
     * soap接口服务端流出输出流处理前拦截器
     */
    @Bean
    public SoapOutPreStreamInterceptor soapOutPreStreamInterceptor(){
        return new SoapOutPreStreamInterceptor();
    }

}
