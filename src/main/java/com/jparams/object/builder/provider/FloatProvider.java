package com.jparams.object.builder.provider;

import java.util.Random;

import com.jparams.object.builder.Context;

public class FloatProvider implements Provider
{
    private final Random random = new Random();

    @Override
    public boolean supports(final Class<?> clazz)
    {
        return clazz.isAssignableFrom(Float.class) || clazz.isAssignableFrom(float.class);
    }

    @Override
    public Float provide(final Context context)
    {
        return random.nextFloat();
    }
}
