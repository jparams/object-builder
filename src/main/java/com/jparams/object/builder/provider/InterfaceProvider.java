package com.jparams.object.builder.provider;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.type.MemberType;
import com.jparams.object.builder.type.MemberTypeResolver;

public class InterfaceProvider implements Provider
{
    @Override
    public boolean supports(final Class<?> clazz)
    {
        return clazz.isInterface();
    }

    @Override
    public Object provide(final Context context)
    {
        final InvocationHandler invocationHandler = new ResponseBuildingInvocationHandler(context);
        final Class<?> type = context.getPath().getMemberType().getType();
        return Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[]{type}, invocationHandler);
    }

    private static class ResponseBuildingInvocationHandler implements InvocationHandler
    {
        private final Context context;
        private final Map<Method, Object> cache = new HashMap<>();

        ResponseBuildingInvocationHandler(final Context context)
        {
            this.context = context;
        }

        @Override
        public Object invoke(final Object proxy, final Method method, final Object[] args)
        {
            if (cache.containsKey(method))
            {
                return cache.get(method);
            }

            final String methodName = String.format("%s(%s)", method.getName(), args == null || args.length == 0 ? "" : Arrays.toString(args));
            final MemberType memberType = MemberTypeResolver.resolve(method);

            if (memberType == null)
            {
                cache.put(method, null);
                return null;
            }

            final Object returnValue = context.createChild(methodName, memberType);
            cache.put(method, returnValue);
            return returnValue;
        }
    }
}
