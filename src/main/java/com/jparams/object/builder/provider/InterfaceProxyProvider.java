package com.jparams.object.builder.provider;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

import com.jparams.object.builder.provider.context.ProviderContext;
import com.jparams.object.builder.type.MemberType;
import com.jparams.object.builder.type.MemberTypeResolver;

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
        final Class<?> type = context.getPath().getMemberType().getType();
        return Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[]{type}, invocationHandler);
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
            final String methodName = String.format("%s(%s", method.getName(), Arrays.toString(args));
            final MemberType memberType = MemberTypeResolver.resolve(method);

            if (memberType == null)
            {
                return null;
            }

            return context.createChild(methodName, memberType);
        }
    }
}
