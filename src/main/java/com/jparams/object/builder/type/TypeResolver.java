package com.jparams.object.builder.type;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.jparams.object.builder.path.Path;

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
     * @param path   path
     * @param method method
     * @return return type
     */
    public static Type<?> resolveReturnType(final Path path, final Method method)
    {
        if (method.getReturnType().equals(Void.TYPE))
        {
            return null;
        }

        return resolveType(path, method.getGenericReturnType());
    }

    /**
     * Resolve the type of a field
     *
     * @param path  path
     * @param field field
     * @return type
     */
    public static Type<?> resolveFieldType(final Path path, final Field field)
    {
        return resolveType(path, field.getGenericType());
    }

    /**
     * Resolve the type of a method parameter
     *
     * @param path      path
     * @param parameter method parameter
     * @return type
     */
    public static Type<?> resolveParameterType(final Path path, final Parameter parameter)
    {
        return resolveType(path, parameter.getParameterizedType());
    }

    /**
     * Resolve a Java Type, this maybe a {@link Class} or a {@link ParameterizedType} into a the internal {@link Type} model
     *
     * @param type java type
     * @return type
     */
    public static Type<?> resolveType(final java.lang.reflect.Type type)
    {
        return resolveType(null, type);
    }

    /**
     * Resolve a Java Type, this maybe a {@link Class} or a {@link ParameterizedType} into a the internal {@link Type} model
     *
     * @param path path
     * @param type java type
     * @return type
     */
    public static Type<?> resolveType(final Path path, final java.lang.reflect.Type type)
    {
        if (type instanceof ParameterizedType)
        {
            final ParameterizedType parameterizedType = (ParameterizedType) type;
            return resolve(path, parameterizedType);
        }

        if (type instanceof Class)
        {
            return new Type<>((Class<?>) type, Collections.emptyList());
        }

        if (type instanceof TypeVariable && path != null)
        {
            final String name = ((TypeVariable) type).getName();
            return path.getType()
                       .getGenerics()
                       .stream()
                       .filter(generic -> generic.getAlias().equals(name)).findFirst()
                       .map(Generic::getType)
                       .orElse(null);
        }

        return null;
    }

    private static Type<?> resolve(final Path path, final ParameterizedType parameterizedType)
    {
        final Class<?> rawType = (Class<?>) parameterizedType.getRawType();

        if (rawType.getTypeParameters().length != parameterizedType.getActualTypeArguments().length)
        {
            return new Type<>(rawType, null); // should not happen, but we will gracefully handle it anyway
        }

        final List<Generic> generics = new ArrayList<>();
        final List<String> aliases = Arrays.stream(rawType.getTypeParameters()).map(TypeVariable::getName).collect(Collectors.toList());

        for (int i = 0; i < aliases.size(); i++)
        {
            final String alias = aliases.get(i);
            final Type type = resolveType(path, parameterizedType.getActualTypeArguments()[i]);
            generics.add(new Generic(alias, type));
        }

        return new Type<>(rawType, generics);
    }
}
