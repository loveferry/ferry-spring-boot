package cn.org.ferry.core.security.filters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 请求包装类
 *      将参数放入attribute中，重写获取参数方法，直接从attribute中获取
 */

public class ParameterRequestWrapper extends HttpServletRequestWrapper {


    public ParameterRequestWrapper(HttpServletRequest request ) {
        super(request);

    }

    @Override
    public String getParameter(String name) {
       return (String) super.getAttribute(name);
    }
}
