package io.github.greenhandsw.split;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@ComponentScan(basePackages = "io.github.greenhandsw.split")
@Slf4j
@Order(-1)
public class DataSourceInterceptor {
//    @Pointcut("execution(* com.shiwei.pay..*(..))")
//    public void queryPointCut(){
//    }
//
//    @Before("queryPointCut()")
//    public void beforeAspect(JoinPoint jp){
//        String className=jp.getTarget().getClass().getSimpleName();
//        log.info("当前类:"+className);
//
//        String methodName = jp.getSignature().getName();
//        log.info("当前方法："+methodName);
//        if(methodName.contains("get") || methodName.contains("find")){
//            DatabaseContextHolder.slave();
//        }else {
//            DatabaseContextHolder.master();
//        }
//    }

//    @Pointcut(value = "execution(* *repository.*.*(..))")
//    public void aroundQueryPointCut(){
//
//    }
//
//    @Around("aroundQueryPointCut()")
//    public Object aroundQueryIntercept(ProceedingJoinPoint pjp) throws Throwable{
//        if (pjp.getTarget().getClass().getAnnotation(ReadOnly))
//    }


    @Pointcut("!@annotation(io.github.greenhandsw.split.annotation.Master) " +
            "&& (execution(* *..repository.*.select*(..)) " +
            "|| execution(* *..repository.*.find*(..))" +
            "|| execution(* *..repository.*.get*(..)))")
    public void readPointcut() {

    }

    @Pointcut("@annotation(io.github.greenhandsw.split.annotation.Master) " +
            "|| execution(* *..repository.*.save*(..)) " +
            "|| execution(* *..repository.*.add*(..)) " +
            "|| execution(* *..repository.*.update*(..)) " +
            "|| execution(* *..repository.*.edit*(..)) " +
            "|| execution(* *..repository.*.delete*(..)) " +
            "|| execution(* *..repository.*.remove*(..)) ")
    public void writePointcut() {

    }

    @Before("readPointcut()")
    public void read() {
        DatabaseContextHolder.slave();
    }

    @Before("writePointcut()")
    public void write() {
        DatabaseContextHolder.master();
    }
}