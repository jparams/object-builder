package com.jparams.object.builder.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * Work up the class tree and discover all fields
     *
     * @param clazz class
     * @return fields
     */
    public static List<Field> getFields(final Class<?> clazz)
    {
        final List<Field> fields = new ArrayList<>();

        Class<?> currentClass = clazz;

        while (currentClass != null)
        {
            for (final Field field : currentClass.getDeclaredFields())
            {
                if (!Modifier.isStatic(field.getModifiers()))
                {
                    fields.add(field);
                }
            }

            currentClass = currentClass.getSuperclass();
        }

        return fields;
    }
}
