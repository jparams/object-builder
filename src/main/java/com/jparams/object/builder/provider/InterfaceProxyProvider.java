package com.jparams.object.builder.provider;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

import com.jparams.object.builder.provider.context.ProviderContext;

public class InterfaceProxyProvider implements Provider
{
    @Override
    public boolean supports(final Class<?> clazz)
    {
        return clazz.isInterface();
    }

    @Override
    public Object provide(final ProviderContext context)
    {
        final InvocationHandler invocationHandler = new ResponseBuildingInvocationHandler(context);
        return Proxy.newProxyInstance(context.getPath().getType().getClassLoader(), new Class<?>[]{context.getPath().getType()}, invocationHandler);
    }

    private static class ResponseBuildingInvocationHandler implements InvocationHandler
    {
        private final ProviderContext context;

        ResponseBuildingInvocationHandler(final ProviderContext context)
        {
            this.context = context;
        }

        @Override
        public Object invoke(final Object proxy, final Method method, final Object[] args)
        {
            if (method.getReturnType().equals(Void.TYPE))
            {
                return null;
            }

            final String methodName = String.format("%s(%s", method.getName(), Arrays.toString(args));
            return context.createChild(methodName, method.getReturnType());
        }
    }
}
