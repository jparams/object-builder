package com.jparams.object.builder.provider;

import java.util.Random;

import com.jparams.object.builder.Context;

public class LongProvider implements Provider
{
    private final Random random = new Random();

    @Override
    public boolean supports(final Class<?> clazz)
    {
        return clazz.isAssignableFrom(Long.class) || clazz.isAssignableFrom(long.class);
    }

    @Override
    public Long provide(final Context context)
    {
        return random.nextLong();
    }
}
