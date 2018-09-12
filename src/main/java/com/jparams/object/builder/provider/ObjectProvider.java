package com.jparams.object.builder.provider;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

import com.jparams.object.builder.BuildStrategy;
import com.jparams.object.builder.Context;
import com.jparams.object.builder.type.MemberType;
import com.jparams.object.builder.type.MemberTypeResolver;
import com.jparams.object.builder.utils.ObjectUtils;

public class ObjectProvider implements Provider
{
    private final BuildStrategy defaultStrategy;
    private final Map<Class<?>, BuildStrategy> strategyMap;

    public ObjectProvider(final BuildStrategy defaultStrategy, final Map<Class<?>, BuildStrategy> strategyMap)
    {
        this.defaultStrategy = defaultStrategy;
        this.strategyMap = strategyMap;
    }

    @Override
    public boolean supports(final Class<?> clazz)
    {
        return !clazz.isPrimitive() && !clazz.isEnum() && !clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers());
    }

    @Override
    public Object provide(final Context context)
    {
        final Class<?> type = context.getPath().getMemberType().getType();
        final BuildStrategy strategy = strategyMap.getOrDefault(type, defaultStrategy);

        switch (strategy)
        {
            case CONSTRUCTOR:
                return createInstanceWithConstructor(context);
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
        try
        {
            return createInstanceWithConstructor(context);
        }
        catch (final Exception e)
        {
            return createInstanceWithFieldInjection(context);
        }
    }

    private Object createInstanceWithConstructor(final Context context)
    {
        final Optional<Constructor<?>> constructor = Arrays.stream(context.getPath().getMemberType().getType().getDeclaredConstructors())
                                                           .sorted(Comparator.comparingInt(Constructor::getParameterCount))
                                                           .peek(c -> c.setAccessible(true))
                                                           .findFirst();

        if (constructor.isPresent())
        {
            return createInstanceWithConstructor(context, constructor.get());
        }

        context.logError("No constructor found");
        return null;
    }

    private Object createInstanceWithConstructor(final Context context, final Constructor<?> constructor)
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
        catch (final Exception e)
        {
            context.logError("Failed to construct an instance. Consider using Field Injection strategy", e);
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
        final Class<?> type = context.getPath().getMemberType().getType();

        try
        {
            final Object instance = ObjectUtils.createInstance(type);
            injectFields(instance, context);
            return instance;
        }
        catch (final Exception e)
        {
            context.logError("Field Injection strategy failed with error. Consider using Constructor Injection strategy.", e);
            return null;
        }
    }

    private void injectFields(final Object object, final Context context)
    {
        for (final Field field : ObjectUtils.getFields(object.getClass()))
        {
            try
            {
                final MemberType memberType = MemberTypeResolver.resolve(field);
                final Object instance = context.createChild(field.getName(), memberType);
                field.setAccessible(true);
                field.set(object, instance);
            }
            catch (final Exception e)
            {
                context.logError("Failed to inject field [" + field.getName() + "]", e);
            }
        }
    }
}