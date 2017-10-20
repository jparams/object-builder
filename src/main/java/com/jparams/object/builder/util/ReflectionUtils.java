package com.jparams.object.builder.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

public class ReflectionUtils
{
    private ReflectionUtils()
    {
    }

    public static String getParametersString(final Constructor<?> constructor)
    {
        return Arrays.stream(constructor.getParameters())
                     .map(param -> param.getType().getSimpleName() + " " + param.getName())
                     .reduce((item1, item2) -> item1 + ", " + item2)
                     .orElse("");
    }

    public static Class<?>[] getGenerics(final Field field)
    {
        if (field.getGenericType() instanceof ParameterizedType)
        {
            return getGenerics((ParameterizedType) field.getGenericType());
        }

        return new Class<?>[0];
    }

    public static Class<?>[] getGenerics(final Parameter parameter)
    {
        if (parameter.getParameterizedType() instanceof ParameterizedType)
        {
            return getGenerics((ParameterizedType) parameter.getParameterizedType());
        }

        return new Class<?>[0];
    }

    public static Class<?>[] getGenerics(final Type type)
    {
        if (type instanceof ParameterizedType)
        {
            return getGenerics((ParameterizedType) type);
        }

        return new Class<?>[0];
    }

    private static Class<?>[] getGenerics(final ParameterizedType parameterizedType)
    {
        return Arrays.stream(parameterizedType.getActualTypeArguments())
                     .map(type -> (Class<?>) type)
                     .toArray(Class<?>[]::new);
    }
}
