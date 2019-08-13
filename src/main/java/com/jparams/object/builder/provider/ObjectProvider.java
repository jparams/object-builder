package com.jparams.object.builder.provider;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

import com.jparams.object.builder.BuildStrategy;
import com.jparams.object.builder.Context;
import com.jparams.object.builder.path.Path;
import com.jparams.object.builder.type.Type;
import com.jparams.object.builder.type.TypeMap;
import com.jparams.object.builder.type.TypeResolver;
import com.jparams.object.builder.util.ObjectUtils;

public class ObjectProvider implements Provider
{
    private final BuildStrategy defaultStrategy;
    private final TypeMap<BuildStrategy> strategyMap;

    public ObjectProvider(final BuildStrategy defaultStrategy, final TypeMap<BuildStrategy> strategyMap)
    {
        this.defaultStrategy = defaultStrategy;
        this.strategyMap = strategyMap;
    }

    @Override
    public boolean supports(final Type<?> type)
    {
        return !type.getJavaType().isPrimitive() && !type.getJavaType().isEnum() && !type.getJavaType().isInterface() && !Modifier.isAbstract(type.getJavaType().getModifiers());
    }

    @Override
    public Object provide(final Context context)
    {
        final BuildStrategy strategy = strategyMap.findMatch(context.getPath().getType()).orElse(defaultStrategy);

        switch (strategy)
        {
            case CONSTRUCTOR:
                return createInstanceWithConstructor(context, true);
            case FIELD_INJECTION:
                return createInstanceWithFieldInjection(context);
            case AUTO:
                return createInstanceWithFallback(context);
            default:
                context.logError("Unknown injection strategy " + strategy);
                return null;
        }
    }

    private Object createInstanceWithFallback(final Context context)
    {
        final Object created = createInstanceWithConstructor(context, false);
        return created == null ? createInstanceWithFieldInjection(context) : created;
    }

    private Object createInstanceWithConstructor(final Context context, final boolean logOnFailure)
    {
        final Optional<Constructor<?>> constructor = Arrays.stream(context.getPath().getType().getJavaType().getDeclaredConstructors())
                                                           .sorted(Comparator.comparingInt(Constructor::getParameterCount))
                                                           .peek(c -> c.setAccessible(true))
                                                           .findFirst();

        if (constructor.isPresent())
        {
            return createInstanceWithConstructor(context, constructor.get(), logOnFailure);
        }

        if (logOnFailure)
        {
            context.logError("No constructor found");
        }

        return null;
    }

    private Object createInstanceWithConstructor(final Context context, final Constructor<?> constructor, final boolean logOnFailure)
    {
        final String name = String.format("%s(%s)", context.getPath().getType().getJavaType().getSimpleName(), getParametersString(constructor));
        final Object[] arguments = new Object[constructor.getParameters().length];

        for (int i = 0; i < constructor.getParameters().length; i++)
        {
            final Parameter parameter = constructor.getParameters()[i];
            final Type<?> type = TypeResolver.resolveParameterType(context.getPath(), parameter);
            arguments[i] = context.createChild(name + "[" + i + "]", type);
        }

        try
        {
            return constructor.newInstance(arguments);
        }
        catch (final Exception e)
        {
            if (logOnFailure)
            {
                context.logError("Failed to build an instance using the constructor build strategy", e);
            }

            return null;
        }
    }

    private String getParametersString(final Constructor<?> constructor)
    {
        return Arrays.stream(constructor.getParameters())
                     .map(param -> param.getType().getSimpleName() + " " + param.getName())
                     .reduce((item1, item2) -> item1 + ", " + item2)
                     .orElse("");
    }

    private Object createInstanceWithFieldInjection(final Context context)
    {
        final Class<?> type = context.getPath().getType().getJavaType();

        try
        {
            final Object instance = ObjectUtils.createInstance(type);
            injectFields(instance, context);
            return instance;
        }
        catch (final Exception e)
        {
            context.logError("Failed to build an instance using the field injection build strategy", e);
            return null;
        }
    }

    private void injectFields(final Object object, final Context context)
    {
        Path path = context.getPath();

        while (true)
        {
            final Class<?> clazz = path.getType().getJavaType();

            for (final Field field : clazz.getDeclaredFields())
            {
                if (Modifier.isStatic(field.getModifiers()))
                {
                    continue;
                }

                try
                {
                    final Type<?> type = TypeResolver.resolveFieldType(path, field);
                    final Object instance = context.createChild(field.getName(), type);
                    field.setAccessible(true);
                    field.set(object, instance);
                }
                catch (final Exception e)
                {
                    context.logError("Failed to inject field [" + field.getName() + "]", e);
                }
            }

            final Type<?> superClass = TypeResolver.resolveType(path, clazz.getGenericSuperclass());

            if (superClass == null)
            {
                return;
            }

            path = new Path("$super", superClass, path);
        }
    }
}