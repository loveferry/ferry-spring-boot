package cn.org.ferry.system.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>swagger 配置类
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/12/22 15:42
 */

@Configuration
@EnableSwagger2
@ConditionalOnExpression("${ferry.swagger.enabled:true}")
public class SwaggerConfiguration {
    private ApiInfo apiInfo(String title, String version, String description) {
        return new ApiInfoBuilder()
                .title(title)
                .version(version)
                .description(description)
                .build();
    }

    private Parameter initParameter(String name, String description, String dataType, String parameterType, boolean required){
        return new ParameterBuilder().name(name)
                .description(description)
                .modelRef(new ModelRef(dataType))
                .parameterType(parameterType)
                .required(required)
                .build();
    }

    /**
     * 人员信息api
     */
    @Bean
    public Docket chinesePeopleDocket() {
        List<Parameter> parameterList = new ArrayList<>(1);
        parameterList.add(initParameter("_token", "token认证", String.class.getSimpleName(), "header", true));

        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/") // 默认请求都是以 / 根路径开始，如果我们的应用不是部署在根路径，比如以/validator部署，则可以通过一下方式设置请求的统一前缀
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.org.ferry"))
                .paths(PathSelectors.ant("/api/chinese/**"))
                .build()
                .groupName("人员信息")
                .globalOperationParameters(parameterList)
                .apiInfo(apiInfo("人员信息", "1.0.0", "当爱已成往事，沉默是最好的道别"));
    }

    /**
     * 系统接口api
     */
    @Bean
    public Docket sysDocket() {
        List<Parameter> parameterList = new ArrayList<>(1);
        parameterList.add(initParameter("_token", "token认证", String.class.getSimpleName(), "header", true));

        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/") // 默认请求都是以 / 根路径开始，如果我们的应用不是部署在根路径，比如以/validator部署，则可以通过一下方式设置请求的统一前缀
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.org.ferry.sys.controllers"))
                .paths(PathSelectors.any())
                .build()
                .groupName("系统模块")
                .globalOperationParameters(parameterList)
                .apiInfo(apiInfo("系统模块", "2.0.0", "你努力后的成功，不能弥补你成功前的痛苦"));
    }
}
