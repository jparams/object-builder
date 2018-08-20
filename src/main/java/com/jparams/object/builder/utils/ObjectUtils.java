package com.jparams.object.builder.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Object utils
 */
public final class ObjectUtils
{
    private ObjectUtils()
    {
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
