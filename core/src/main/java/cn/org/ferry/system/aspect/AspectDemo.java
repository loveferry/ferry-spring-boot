package cn.org.ferry.system.aspect;

import cn.org.ferry.sys.dto.ChinesePeople;
import org.aspectj.lang.annotation.*;

@Aspect
public class AspectDemo {
    @Pointcut("execution (* cn.org.ferry.sys.service.ChinesePeopleService.query(..))")
    public void query(){}

    @Pointcut("execution (* cn.org.ferry.sys.service.ChinesePeopleService.query(..)) && args(chinesePeople, page, pageSize)")
    public void queryWithParams(ChinesePeople chinesePeople, int page, int pageSize){}

    @Before("query()")
    public void say(){
        System.out.println("before query.......");
    }

    @AfterReturning("query()")
    public void afterReturning(){
        System.out.println("after returning ....");
    }

    @AfterThrowing("query()")
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

    @Before("queryWithParams(chinesePeople, page, pageSize)")
    public void sayWithParams(ChinesePeople chinesePeople, int page, int pageSize){
        System.out.println("before query with params " + chinesePeople.toString() + " and page is " + page + ", pageSize is " + pageSize);
    }
}
