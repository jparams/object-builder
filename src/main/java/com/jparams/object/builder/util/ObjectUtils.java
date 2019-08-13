package com.jparams.object.builder.util;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

/**
 * Object utils
 */
public final class ObjectUtils
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

    private ObjectUtils()
    {
    }

    @SuppressWarnings("unchecked")
    public static <T> T createInstance(final Class<T> clazz)
    {
        if (unsafe == null)
        {
            throw new UnsupportedOperationException("Failed to create an instance without constructor injection");
        }

        try
        {
            return (T) unsafe.allocateInstance(clazz);
        }
        catch (final InstantiationException e)
        {
            throw new UnsupportedOperationException("Failed to create an instance without constructor injection", e);
        }
    }
}
