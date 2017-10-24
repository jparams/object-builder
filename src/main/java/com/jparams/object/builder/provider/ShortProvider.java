package com.jparams.object.builder.provider;

import java.util.Random;

import com.jparams.object.builder.Context;

public class ShortProvider implements Provider
{
    private final Random random = new Random();

    @Override
    public boolean supports(final Class<?> clazz)
    {
        return clazz.isAssignableFrom(Short.class) || clazz.isAssignableFrom(short.class);
    }

    @Override
    public Short provide(final Context context)
    {
        return (short) random.nextInt(Short.MAX_VALUE);
    }
}
