package com.jparams.object.builder.provider;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.type.MemberType;
import com.jparams.object.builder.type.MemberTypeResolver;

public class ObjectProvider implements Provider
{
    @Override
    public boolean supports(final Class<?> clazz)
    {
        return !clazz.isPrimitive() && !clazz.isEnum() && !clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers());
    }

    @Override
    public Object provide(final Context context)
    {
        final List<Constructor<?>> constructors = getAllConstructors(context.getPath().getMemberType().getType());

        for (final Constructor<?> constructor : constructors)
        {
            final Object instance = createInstance(constructor, context);

            if (instance != null)
            {
                injectFields(instance, context);
                return instance;
            }
        }

        context.logError("No constructor found");
        return null;
    }


    private Object createInstance(final Constructor<?> constructor, final Context context)
    {
        final String name = String.format("%s(%s)", context.getPath().getMemberType().getType().getSimpleName(), getParametersString(constructor));
        final Object[] arguments = new Object[constructor.getParameters().length];

        for (int i = 0; i < constructor.getParameters().length; i++)
        {
            final Parameter parameter = constructor.getParameters()[i];
            final MemberType memberType = MemberTypeResolver.resolve(parameter);
            arguments[i] = context.createChild(name + "[" + i + "]", memberType);
        }

        try
        {
            return constructor.newInstance(arguments);
        }
        catch (InstantiationException | IllegalAccessException | InvocationTargetException | IllegalArgumentException e)
        {
            context.logError("Failed to construct a new instance", e);
            return null;
        }
    }

    private void injectFields(final Object object, final Context context)
    {
        Class<?> clazz = object.getClass();

        while (clazz != null)
        {
            for (final Field field : clazz.getDeclaredFields())
            {
                if (!Modifier.isFinal(field.getModifiers()))
                {
                    final MemberType memberType = MemberTypeResolver.resolve(field);
                    final Object instance = context.createChild(field.getName(), memberType);

                    try
                    {
                        field.setAccessible(true);
                        field.set(object, instance);
                    }
                    catch (final IllegalAccessException e)
                    {
                        context.logError("Failed to inject field", e);
                    }
                }
            }

            clazz = clazz.getSuperclass();
        }
    }

    private static List<Constructor<?>> getAllConstructors(final Class<?> clazz)
    {
        return Arrays.stream(clazz.getDeclaredConstructors())
                     .sorted(Comparator.comparingInt(Constructor::getParameterCount))
                     .peek(constructor -> constructor.setAccessible(true))
                     .collect(Collectors.toList());
    }

    private static String getParametersString(final Constructor<?> constructor)
    {
        return Arrays.stream(constructor.getParameters())
                     .map(param -> param.getType().getSimpleName() + " " + param.getName())
                     .reduce((item1, item2) -> item1 + ", " + item2)
                     .orElse("");
    }
}
