package com.jparams.object.builder.provider;

import java.util.Random;

import com.jparams.object.builder.Context;

public class IntegerProvider implements Provider
{
    private final Random random = new Random();

    @Override
    public boolean supports(final Class<?> clazz)
    {
        return clazz.isAssignableFrom(Integer.class) || clazz.isAssignableFrom(int.class);
    }

    @Override
    public Integer provide(final Context context)
    {
        return random.nextInt();
    }
}
