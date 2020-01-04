package cn.org.ferry.sys.aspects;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Aspect
public class AspectDemo {
    private static final Logger logger = LoggerFactory.getLogger(AspectDemo.class);

    @Pointcut("execution (* cn.org.ferry.sys.service.SysSqlService.execute(..))")
    public void execute(){}

    @Pointcut("execution (* cn.org.ferry.sys.service.SysSqlService.execute(..)) && args(sql, params)")
    public void queryWithParams(String sql, Map<String, Object> params){}

    @Before("execute()")
    public void say(){
        System.out.println("before query.......");
    }

    @AfterReturning("execute()")
    public void afterReturning(){
        System.out.println("after returning ....");
    }

    @AfterThrowing("execute()")
    public void afterThrow(){
        System.out.println("after throw.....");
    }

    /*@Around("query()")
    public void around(ProceedingJoinPoint proceedingJoinPoint){
        try {
            System.out.println("around before.....");
            proceedingJoinPoint.proceed();
            System.out.println("around return after.....");
        } catch (Throwable throwable) {
            System.out.println("around throw after......");
        }
    }*/

    @Before("queryWithParams(sql, params)")
    public void sayWithParams(String sql, Map<String, Object> params){
        logger.info("execute sql: {}", sql);
    }
}
