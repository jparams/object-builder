package com.jparams.object.builder.type;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TypeResolver
{
    private TypeResolver()
    {
    }

    public static Type resolve(final Method method)
    {
        if (method.getReturnType().equals(Void.TYPE))
        {
            return null;
        }

        return resolve(method.getGenericReturnType());
    }

    public static Type resolve(final Field field)
    {
        return resolve(field.getGenericType());
    }

    public static Type resolve(final Parameter parameter)
    {
        return resolve(parameter.getParameterizedType());
    }

    public static Type resolve(final java.lang.reflect.Type type)
    {
        if (type instanceof ParameterizedType)
        {
            return resolve((ParameterizedType) type);
        }

        if (type instanceof Class)
        {
            return new Type((Class<?>) type, Collections.emptyList());
        }

        return null;
    }

    private static Type resolve(final ParameterizedType parameterizedType)
    {
        final java.lang.reflect.Type type = parameterizedType.getRawType();
        final List<Type> generics = Arrays.stream(parameterizedType.getActualTypeArguments())
                                          .map(TypeResolver::resolve)
                                          .filter(Objects::nonNull)
                                          .collect(Collectors.toList());

        return new Type((Class<?>) type, generics);
    }
}
