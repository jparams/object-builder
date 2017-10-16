package com.jparams.object.builder.path;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class PathFactory
{
    private PathFactory()
    {
    }

    public static Path createPath(final String name, final Class<?> clazz, final Path parentPath)
    {
        return new Path(name, clazz, Collections.emptyList(), parentPath);
    }

    public static Path createRootPath(final Class<?> clazz)
    {
        return createPath("$", clazz, null);
    }

    public static Path createConstructorPath(final Constructor<?> constructor, final Parameter parameter, final Path parent)
    {
        final String parameters = Arrays.stream(constructor.getParameters())
                                        .map(param -> param.getType().getSimpleName() + " " + param.getName())
                                        .reduce((item1, item2) -> item1 + ", " + item2)
                                        .orElse("");

        final String pathName = String.format("constructor(%s).%s", parameters, parameter.getName());

        return new Path(pathName, parameter.getType(), getGenerics(parameter), parent);
    }

    public static Path createFieldPath(final Field field, final Path parent)
    {
        return new Path(field.getName(), field.getType(), getGenerics(field), parent);
    }

    private static List<Class<?>> getGenerics(final Field field)
    {
        if (field.getGenericType() instanceof ParameterizedType)
        {
            final ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();

            return Arrays.stream(parameterizedType.getActualTypeArguments())
                         .map(type -> (Class<?>) type)
                         .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }

    private static List<Class<?>> getGenerics(final Parameter parameter)
    {
        if (parameter.getParameterizedType() instanceof ParameterizedType)
        {
            final ParameterizedType parameterizedType = (ParameterizedType) parameter.getParameterizedType();

            return Arrays.stream(parameterizedType.getActualTypeArguments())
                         .map(type -> (Class<?>) type)
                         .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }
}
