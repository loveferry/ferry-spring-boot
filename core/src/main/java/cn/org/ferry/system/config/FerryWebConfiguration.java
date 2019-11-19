package cn.org.ferry.system.config;

import cn.org.ferry.system.dto.FerryRequest;
import cn.org.ferry.system.dto.FerrySession;
import cn.org.ferry.system.inceptors.AuthenticationInterceptor;
import cn.org.ferry.system.inceptors.RegisterRequestInterceptor;
import cn.org.ferry.system.utils.PropertiesUtils;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;

/**
 * 拦截器配置类
 */
@Configuration
public class FerryWebConfiguration implements WebMvcConfigurer {
    /**
     * 会话 bean
     */
    @Bean
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public FerrySession ferrySession(){
        FerrySession ferrySession = new FerrySession();
        return ferrySession;
    }

    /**
     * 会话 bean
     */
    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public FerryRequest ferryRequest(FerrySession ferrySession){
        FerryRequest ferryRequest = new FerryRequest(ferrySession);
        ferryRequest.setNow(new Date());
        return ferryRequest;
    }

    /**
     * token认证拦截器
     */
    @Bean
    public RegisterRequestInterceptor registerRequestInterceptor() {
        FerrySession ferrySession = ferrySession();
        return new RegisterRequestInterceptor(ferrySession, ferryRequest(ferrySession));
    }

    /**
     * token认证拦截器
     */
    @Bean
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(authenticationInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(registerRequestInterceptor()).addPathPatterns("/**");
    }

    /*@Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(currentUserMethodArgumentResolver());
    }

    @Bean
    public CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver() {
        return new CurrentUserMethodArgumentResolver();
    }*/

    /**
     * 定制 http 消息转换器: json 解析采用 fastJson 框架解析
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.WriteMapNullValue,        // 保留空的字段
                SerializerFeature.WriteNullStringAsEmpty,   // String null -> ""
                SerializerFeature.WriteDateUseDateFormat,   // String null -> ""
                SerializerFeature.WriteNullListAsEmpty      // List null-> []
        );
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        fastJsonHttpMessageConverter.setDefaultCharset(Charset.forName(PropertiesUtils.DEFAULT_CHARSET));
        converters.add(0, fastJsonHttpMessageConverter);
//        converters.add(fastJsonHttpMessageConverter);
    }

    /*
    QuoteFieldNames	输出key时是否使用双引号,默认为true
    UseSingleQuotes	使用单引号而不是双引号,默认为false
    WriteMapNullValue	是否输出值为null的字段,默认为false
    WriteEnumUsingToString	Enum输出name()或者original,默认为false
    UseISO8601DateFormat	Date使用ISO8601格式输出，默认为false
    WriteNullListAsEmpty	List字段如果为null,输出为[],而非null
    WriteNullStringAsEmpty	字符类型字段如果为null,输出为”“,而非null
    WriteNullNumberAsZero	数值字段如果为null,输出为0,而非null
    WriteNullBooleanAsFalse	Boolean字段如果为null,输出为false,而非null
    SkipTransientField	如果是true，类中的Get方法对应的Field是transient，序列化时将会被忽略。默认为true
    SortField	按字段名称排序后输出。默认为false
    WriteTabAsSpecial	把\t做转义输出，默认为false	不推荐
    PrettyFormat	结果是否格式化,默认为false
    WriteClassName	序列化时写入类型信息，默认为false。反序列化是需用到
    DisableCircularReferenceDetect	消除对同一对象循环引用的问题，默认为false
    WriteSlashAsSpecial	对斜杠’/’进行转义
    BrowserCompatible	将中文都会序列化为\\uXXXX格式，字节数会多一些，但是能兼容IE 6，默认为false
    WriteDateUseDateFormat	全局修改日期格式,默认为false。JSON.DEFFAULT_DATE_FORMAT = “yyyy-MM-dd”;JSON.toJSONString(obj, SerializerFeature.WriteDateUseDateFormat);
    DisableCheckSpecialChar	一个对象的字符串属性中如果有特殊字符如双引号，将会在转成json时带有反斜杠转移符。如果不需要转义，可以使用这个属性。默认为false
    NotWriteRootClassName	含义
    BeanToArray	将对象转为array输出
    WriteNonStringKeyAsString	含义
    NotWriteDefaultValue	含义
    BrowserSecure	含义
    IgnoreNonFieldGetter	含义
    WriteEnumUsingName	含义
    */
}
