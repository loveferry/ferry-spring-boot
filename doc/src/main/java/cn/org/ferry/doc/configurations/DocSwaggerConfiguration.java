package cn.org.ferry.doc.configurations;

import cn.org.ferry.core.utils.SwaggerUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <p>swagger 配置类
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/12/22 15:42
 */

@Configuration
@EnableSwagger2
@ConditionalOnExpression("${ferry.swagger.enabled:true}")
public class DocSwaggerConfiguration {

    /**
     * 文档操作接口api
     */
    @Bean
    public Docket docDocket() {
//        List<Parameter> parameterList = new ArrayList<>(1);
//        parameterList.add(SwaggerUtils.initParameter("_token", "token认证", String.class.getSimpleName(), "header", true));

        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/") // 默认请求都是以 / 根路径开始，如果我们的应用不是部署在根路径，比如以/validator部署，则可以通过一下方式设置请求的统一前缀
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.org.ferry.doc.controllers"))
                .paths(PathSelectors.any())
                .build()
                .groupName("文档模块")
//                .globalOperationParameters(parameterList)
                .apiInfo(SwaggerUtils.apiInfo("文档模块", "2.0.0", "当爱已成往事，沉默是最好的道别"));
    }
}
