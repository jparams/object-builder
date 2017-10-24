package com.jparams.object.builder.type;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MemberTypeResolver
{
    private MemberTypeResolver()
    {
    }

    public static MemberType resolve(final Method method)
    {
        if (method.getReturnType().equals(Void.TYPE))
        {
            return null;
        }

        return resolve(method.getGenericReturnType());
    }

    public static MemberType resolve(final Field field)
    {
        return resolve(field.getGenericType());
    }

    public static MemberType resolve(final Parameter parameter)
    {
        return resolve(parameter.getParameterizedType());
    }

    public static MemberType resolve(final Type type)
    {
        if (type instanceof ParameterizedType)
        {
            return resolve((ParameterizedType) type);
        }

        if (type instanceof Class)
        {
            return new MemberType((Class<?>) type);
        }

        return null;
    }


    private static MemberType resolve(final ParameterizedType parameterizedType)
    {
        final Type type = parameterizedType.getRawType();
        final List<MemberType> generics = Arrays.stream(parameterizedType.getActualTypeArguments())
                                                .map(MemberTypeResolver::resolve)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.toList());

        return new MemberType((Class<?>) type, generics);
    }
}
