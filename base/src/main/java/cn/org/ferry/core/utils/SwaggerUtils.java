package cn.org.ferry.core.utils;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;

/**
 * <p>swagger工具类
 *
 * @author ferry ferry_sy@163.com
 * created by 2020/01/13 14:49
 */

public final class SwaggerUtils {

    private SwaggerUtils(){}

    /**
     * 文档api描述
     * @param title 标题
     * @param version 版本
     * @param description 描述
     * @return 返回描述对象
     */
    public static ApiInfo apiInfo(String title, String version, String description) {
        return new ApiInfoBuilder()
                .title(title)
                .version(version)
                .description(description)
                .build();
    }

    /**
     * 初始化全局参数
     * @param name 名称
     * @param description 描述
     * @param dataType 类型
     * @param parameterType 参数类型
     * @param required 是否必填
     * @return 参数对象
     */
    public static Parameter initParameter(String name, String description, String dataType, String parameterType, boolean required){
        return new ParameterBuilder().name(name)
                .description(description)
                .modelRef(new ModelRef(dataType))
                .parameterType(parameterType)
                .required(required)
                .build();
    }
}
