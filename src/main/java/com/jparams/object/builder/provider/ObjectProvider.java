package com.jparams.object.builder.provider;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.jparams.object.builder.factory.ObjectFactory;
import com.jparams.object.builder.path.Path;
import com.jparams.object.builder.path.PathFactory;
import com.jparams.object.builder.provider.context.ProviderContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectProvider implements Provider
{
    private static final Logger LOG = LoggerFactory.getLogger(ObjectProvider.class);

    @Override
    public Object provide(final ProviderContext providerContext)
    {
        final List<Constructor<?>> constructors = getAllConstructors(path.getType());

        for (final Constructor<?> constructor : constructors)
        {
            final Object instance = createInstance(path, constructor, objectFactory);

            if (instance != null)
            {
                injectFields(instance, path, objectFactory);
                return instance;
            }
        }

        return null;
    }

    @Override
    public boolean supports(final Class<?> clazz)
    {
        return !clazz.isPrimitive() && !clazz.isEnum() && !clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers());
    }

    private Object createInstance(final Path path, final Constructor<?> constructor, final ObjectFactory objectFactory)
    {
        final Object[] parameters = Arrays.stream(constructor.getParameters())
                                          .map(parameter -> PathFactory.createConstructorPath(constructor, parameter, path))
                                          .map(objectFactory::create)
                                          .toArray();

        try
        {
            return constructor.newInstance(parameters);
        }
        catch (InstantiationException | IllegalAccessException | InvocationTargetException | IllegalArgumentException e)
        {
            LOG.trace("Failed to createRootPath instance of [{}] at path [{}]. Failed with exception", path.getType(), path.getLocation(), e);
            return null;
        }
    }

    private void injectFields(final Object object, final Path parentPath, final ObjectFactory objectFactory)
    {
        Class<?> clazz = object.getClass();

        while (clazz != null)
        {
            for (final Field field : clazz.getDeclaredFields())
            {
                if (!Modifier.isFinal(field.getModifiers()))
                {
                    final Path path = PathFactory.createFieldPath(field, parentPath);
                    final Object instance = objectFactory.create(path);

                    try
                    {
                        field.setAccessible(true);
                        field.set(object, instance);
                    }
                    catch (final IllegalAccessException e)
                    {
                        LOG.trace("Failed to createRootPath instance of [{}] at path [{}]. Failed with exception", path.getType(), path.getLocation(),
                                  e);
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
}
