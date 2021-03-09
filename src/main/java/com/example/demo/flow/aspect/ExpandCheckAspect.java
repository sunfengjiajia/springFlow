package com.example.demo.flow.aspect;

import com.example.demo.flow.StateFlow;
import com.example.demo.flow.annotition.ExpandCheckAnno;
import com.example.demo.flow.util.SpringUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.xml.transform.Result;
import java.util.HashMap;

/**
 * @author 孙丰佳
 * @version V1.0
 * @Title: ExpandCheckAspect
 * @Package com.example.demo.flow.aspect
 * @Description: (用一句话描述该文件做什么)
 * @date 2021-02-04 11:18
 **/
@Aspect
@Component
public class ExpandCheckAspect {
    @Pointcut("@annotation(com.example.demo.flow.annotition.ExpandCheckAnno)")
    public void BrokerAspect() {}

    @Around(value = "BrokerAspect() && @annotation(anno)")
    public Object doAround(ProceedingJoinPoint joinPoint, ExpandCheckAnno anno) throws Throwable {
        String[] beforeServiceNames = anno.beforeServiceNames();
        for (String beforeServiceName : beforeServiceNames) {
            IExpandCheck bean = SpringUtil.getBean(beforeServiceName, IExpandCheck.class);
            bean.run((StateFlow) joinPoint.getTarget());
        }

        Object result = joinPoint.proceed();

        if (result instanceof Boolean && (Boolean)result) {
            String[] afterServiceNames = anno.afterServiceNames();
            for (String afterServiceName : afterServiceNames) {
                IExpandCheck bean = SpringUtil.getBean(afterServiceName, IExpandCheck.class);
                bean.run((StateFlow) joinPoint.getTarget());
            }
        }
        //返回值逻辑
        return result;
    }
}
