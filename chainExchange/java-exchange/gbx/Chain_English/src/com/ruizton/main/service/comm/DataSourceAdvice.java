package com.ruizton.main.service.comm;
import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;

public class DataSourceAdvice implements MethodBeforeAdvice, AfterReturningAdvice, ThrowsAdvice {
    // service方法执行之前被调用
    public void before(Method method, Object[] args, Object target) throws Throwable {
        if(method.getName().startsWith("add") 
            || method.getName().startsWith("save")
            || method.getName().startsWith("insert")
            || method.getName().startsWith("update")
            || method.getName().startsWith("delete")){
//            //System.out.println("切换到: master");
//	        //System.out.println("切入点: " + target.getClass().getName() + "类中" + method.getName() + "方法");
            DataSourceSwitcher.setMaster();
        }
        else  {
//            //System.out.println("切换到: slave");
//            //System.out.println("切入点: " + target.getClass().getName() + "类中" + method.getName() + "方法");
            DataSourceSwitcher.setSlave();
        }
    }

    // service方法执行完之后被调用
    public void afterReturning(Object arg0, Method method, Object[] args, Object target) throws Throwable {
    }

    // 抛出Exception之后被调用
    public void afterThrowing(Method method, Object[] args, Object target, Exception ex) throws Throwable {
        DataSourceSwitcher.setSlave();
        //System.out.println("出现异常,切换到: slave");
    }

}