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

import com.jparams.object.builder.path.Path;
import com.jparams.object.builder.provider.context.ProviderContext;
import com.jparams.object.builder.type.MemberType;
import com.jparams.object.builder.type.MemberTypeResolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectProvider implements Provider
{
    private static final Logger LOG = LoggerFactory.getLogger(ObjectProvider.class);

    @Override
    public boolean supports(final Class<?> clazz)
    {
        return !clazz.isPrimitive() && !clazz.isEnum() && !clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers());
    }

    @Override
    public Object provide(final ProviderContext context)
    {
        final List<Constructor<?>> constructors = getAllConstructors(context.getPath().getMemberType().getType());

        for (final Constructor<?> constructor : constructors)
        {
            final Object instance = createInstance(context.getPath(), constructor, context);

            if (instance != null)
            {
                injectFields(instance, context);
                return instance;
            }
        }

        return null;
    }


    private Object createInstance(final Path path, final Constructor<?> constructor, final ProviderContext context)
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
            LOG.trace("Failed to createRootPath instance of [{}] at path [{}]. Failed with exception", path.getMemberType(), path.getLocation(), e);
            return null;
        }
    }

    private void injectFields(final Object object, final ProviderContext context)
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
                        final Path path = context.getPath();
                        LOG.trace("Failed to instance of [{}] at path [{}]. Failed with exception", path.getMemberType(), path.getLocation(), e);
                    }
                }
            }

            clazz = clazz.getSuperclass();
        }
    }

    /**
     * Get all constructor ordered by largest parameter count
     *
     * @param clazz class to scan
     * @return constructors
     */
    private static List<Constructor<?>> getAllConstructors(final Class<?> clazz)
    {
        return Arrays.stream(clazz.getDeclaredConstructors())
                     .sorted(Comparator.comparingInt(Constructor::getParameterCount))
                     .peek(constructor -> constructor.setAccessible(true))
                     .collect(Collectors.toList());
    }

    public static String getParametersString(final Constructor<?> constructor)
    {
        return Arrays.stream(constructor.getParameters())
                     .map(param -> param.getType().getSimpleName() + " " + param.getName())
                     .reduce((item1, item2) -> item1 + ", " + item2)
                     .orElse("");
    }
}
