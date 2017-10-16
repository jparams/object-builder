package com.jparams.object.builder.provider;

import java.lang.reflect.Array;
import java.util.Random;

import com.jparams.object.builder.provider.context.ProviderContext;

public class ArrayProvider implements Provider
{
    private final Random random = new Random();

    @Override
    public boolean supports(final Class<?> clazz)
    {
        return clazz.isArray();
    }

    @Override
    public Object[] provide(final ProviderContext context)
    {
        final Class<?> type = context.getPath().getType().getComponentType();
        final Object[] array = (Object[]) Array.newInstance(type, randomSize());

        for (int i = 0; i < array.length; i++)
        {
            array[i] = context.createChild("[" + i + "]", type);
        }

        return array;
    }

    private int randomSize()
    {
        final int size = random.nextInt(5);
        return size > 0 ? size : 1;
    }
}
