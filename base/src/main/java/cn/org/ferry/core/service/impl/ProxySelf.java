package cn.org.ferry.core.service.impl;

import org.springframework.aop.framework.AopContext;

/**
 * <p>spring 组件自身代理接口
 *      实现此接口可通过 self 方法调用自身的其他方法获取的spring 动态代理过程中切面完成的工作，例如 spring 声明式事务
 *
 * @author ferry ferry_sy@163.com
 * created by 2020/06/20 12:45
 */

public interface ProxySelf<T> {
    /**
     * 获取当前代理的对象
     */
    @SuppressWarnings("unchecked")
    default T self(){
        return (T)AopContext.currentProxy();
    }
}
