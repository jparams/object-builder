package com.jparams.object.builder.provider;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.type.MemberType;
import com.jparams.object.builder.type.MemberTypeResolver;
import com.jparams.object.builder.utils.ObjectUtils;

import sun.misc.Unsafe;

public class ObjectProvider implements Provider
{
    private static Unsafe unsafe;

    static
    {
        try
        {
            final Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        }
        catch (final Exception e)
        {
            unsafe = null;
        }
    }

    private final InjectionStrategy injectionStrategy;

    public ObjectProvider(final InjectionStrategy injectionStrategy)
    {
        this.injectionStrategy = injectionStrategy;
    }

    @Override
    public boolean supports(final Class<?> clazz)
    {
        return !clazz.isPrimitive() && !clazz.isEnum() && !clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers());
    }

    @Override
    public Object provide(final Context context)
    {
        if (injectionStrategy == InjectionStrategy.CONSTRUCTOR_INJECTION)
        {
            return createInstanceWithConstructor(context);
        }
        else if (injectionStrategy == InjectionStrategy.FIELD_INJECTION)
        {
            return createInstanceWithFieldInjection(context);
        }
        else
        {
            context.logError("Unknown injection strategy " + injectionStrategy);
            return null;
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
        catch (InstantiationException | IllegalAccessException | InvocationTargetException | IllegalArgumentException e)
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
        if (unsafe == null)
        {
            context.logError("Field Injection strategy is not supported.  Consider using Constructor Injection strategy.");
            return null;
        }

        final Class<?> type = context.getPath().getMemberType().getType();

        try
        {
            final Object instance = unsafe.allocateInstance(type);
            injectFields(instance, context);
            return instance;
        }
        catch (final InstantiationException e)
        {
            context.logError("Field Injection strategy failed with error. Consider using Constructor Injection strategy.", e);
            return null;
        }
    }

    private void injectFields(final Object object, final Context context)
    {
        for (final Field field : ObjectUtils.getFields(object.getClass()))
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

    public enum InjectionStrategy
    {
        /**
         * Constructs an instance of an object using the best possible constructor
         */
        CONSTRUCTOR_INJECTION,

        /**
         * Constructs an instance using field injection.
         */
        FIELD_INJECTION
    }
}