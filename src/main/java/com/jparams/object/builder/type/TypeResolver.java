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

/**
 * Resolves the type of various java member types and return a {@link Type} object
 */
public class TypeResolver
{
    private TypeResolver()
    {
    }

    /**
     * Resolve the return type of method
     *
     * @param method method
     * @return return type
     */
    public static Type<?> resolve(final Method method)
    {
        if (method.getReturnType().equals(Void.TYPE))
        {
            return null;
        }

        return resolve(method.getGenericReturnType());
    }

    /**
     * Resolve the type of a field
     *
     * @param field field
     * @return type
     */
    public static Type<?> resolve(final Field field)
    {
        return resolve(field.getGenericType());
    }

    /**
     * Resolve the type of a method parameter
     *
     * @param parameter method parameter
     * @return type
     */
    public static Type<?> resolve(final Parameter parameter)
    {
        return resolve(parameter.getParameterizedType());
    }

    /**
     * Resolve a Java Type, this maybe a {@link Class} or a {@link ParameterizedType} into a the internal {@link Type} model
     *
     * @param type java type
     * @return type
     */
    public static Type<?> resolve(final java.lang.reflect.Type type)
    {
        if (type instanceof ParameterizedType)
        {
            return resolve((ParameterizedType) type);
        }

        if (type instanceof Class)
        {
            return new Type<>((Class<?>) type, Collections.emptyList());
        }

        return null;
    }

    private static Type<?> resolve(final ParameterizedType parameterizedType)
    {
        final java.lang.reflect.Type type = parameterizedType.getRawType();
        final List<Type<?>> generics = Arrays.stream(parameterizedType.getActualTypeArguments())
                                             .map(TypeResolver::resolve)
                                             .filter(Objects::nonNull)
                                             .collect(Collectors.toList());

        return new Type<>((Class<?>) type, generics);
    }
}
