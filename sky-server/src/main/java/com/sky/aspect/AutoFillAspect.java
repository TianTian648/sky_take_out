package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;

/**
 * 自定义切面，实现公共字段自动填充的逻辑
 */
@Slf4j
@Component
@Aspect
public class AutoFillAspect {
    /**
     * 切入点*/
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public  void  autoFillPointCut(){}
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        log.info("开始进行公共字段填充");
       //获取当前被拦截方法的数据操作类型
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        OperationType operationType = signature.getMethod().getAnnotation(AutoFill.class).value();
        //获取当前被拦截方法的参数--实体对象
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return;
        }

        Object entity = args[0];

        //准备赋值的数据
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();
        //通过反射来赋值
        if (operationType == OperationType.INSERT) {
            entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME,LocalDateTime.class).invoke(entity, now);
            entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME,LocalDateTime.class).invoke(entity, now);
            entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER,Long.class).invoke(entity, currentId);
            entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER,Long.class).invoke(entity, currentId);
        } else if (operationType == OperationType.UPDATE) {
            entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME,LocalDateTime.class).invoke(entity, now);
            entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER,Long.class).invoke(entity, currentId);
        }
    }
}
