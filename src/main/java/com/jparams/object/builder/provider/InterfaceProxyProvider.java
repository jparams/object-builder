package com.jparams.object.builder.provider;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

import com.jparams.object.builder.factory.ObjectFactory;
import com.jparams.object.builder.path.Path;
import com.jparams.object.builder.path.PathFactory;
import com.jparams.object.builder.provider.context.ProviderContext;

public class InterfaceProxyProvider implements Provider
{
    @Override
    public Object provide(final ProviderContext providerContext)
    {
        final InvocationHandler invocationHandler = new ResponseBuildingInvocationHandler(objectFactory, path);
        return Proxy.newProxyInstance(path.getType().getClassLoader(), new Class<?>[]{path.getType()}, invocationHandler);
    }

    @Override
    public boolean supports(final Class<?> clazz)
    {
        return clazz.isInterface();
    }

    private static class ResponseBuildingInvocationHandler implements InvocationHandler
    {
        private final ObjectFactory objectFactory;
        private final Path path;

        ResponseBuildingInvocationHandler(final ObjectFactory objectFactory, final Path path)
        {
            this.objectFactory = objectFactory;
            this.path = path;
        }

        @Override
        public Object invoke(final Object proxy, final Method method, final Object[] args)
        {
            if (method.getReturnType().equals(Void.TYPE))
            {
                return null;
            }

            final String methodName = String.format("%s(%s", method.getName(), Arrays.toString(args));
            return objectFactory.create(PathFactory.createPath(methodName, method.getReturnType(), path));
        }
    }
}
