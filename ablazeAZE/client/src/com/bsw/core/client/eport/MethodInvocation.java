/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsw.core.client.eport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 调用代理对象方法
 *
 * @author xc
 */
public class MethodInvocation implements InvocationHandler {

    /**
     * 目标对象
     */
    private Object target = null;

    public MethodInvocation(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, final Object[] args) throws Throwable {
        String methodName = method.getName();
        Class<?>[] paraTypes = method.getParameterTypes();
        final Method targetMethod = target.getClass().getMethod(methodName, paraTypes);
        System.out.println("target method:"+targetMethod);
        return targetMethod.invoke(target, args);
    }

}
