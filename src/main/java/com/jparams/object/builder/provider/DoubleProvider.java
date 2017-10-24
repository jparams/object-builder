package com.jparams.object.builder.provider;

import java.util.Random;

import com.jparams.object.builder.Context;

public class DoubleProvider implements Provider
{
    private final Random random = new Random();

    @Override
    public boolean supports(final Class<?> clazz)
    {
        return clazz.isAssignableFrom(Double.class) || clazz.isAssignableFrom(double.class);
    }

    @Override
    public Double provide(final Context context)
    {
        return random.nextDouble();
    }

}
