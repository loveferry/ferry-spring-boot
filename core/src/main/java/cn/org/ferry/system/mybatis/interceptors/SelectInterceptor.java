package cn.org.ferry.system.mybatis.interceptors;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;

import java.sql.Statement;
import java.util.Properties;

@Intercepts({
        @Signature(
                type = StatementHandler.class,
                method = "query",
                args = {
                        Statement.class,
                        ResultHandler.class
                })
})
public class SelectInterceptor implements Interceptor {
    /**
     * 定义自己的Interceptor最重要的是要实现plugin方法和intercept方法，
     * 在plugin方法中我们可以决定是否要进行拦截进而决定要返回一个什么样的目标对象。
     * 而intercept方法就是要进行拦截的时候要执行的方法。
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object o = invocation.proceed();
        return o;
    }

    /**
     * plugin方法是拦截器用于封装目标对象的，
     * 通过该方法我们可以返回目标对象本身，
     * 也可以返回一个它的代理。
     * 当返回的是代理的时候我们可以对其中的方法进行拦截来调用intercept方法，
     * 当然也可以调用其他方法。
     */
    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    /**
     * setProperties方法是用于在Mybatis配置文件中指定一些属性的。
     */
    @Override
    public void setProperties(Properties properties) {

    }
}
