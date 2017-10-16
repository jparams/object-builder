package com.jparams.object.builder.provider;

import java.util.Random;

import com.jparams.object.builder.provider.context.ProviderContext;

public class FloatProvider implements Provider
{
    private final Random random;

    public FloatProvider()
    {
        random = new Random();
    }

    @Override
    public Float provide(final ProviderContext providerContext)
    {
        return random.nextFloat();
    }

    @Override
    public boolean supports(final Class<?> clazz)
    {
        return clazz.isAssignableFrom(Float.class) || clazz.isAssignableFrom(float.class);
    }
}
