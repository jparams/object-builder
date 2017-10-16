package com.jparams.object.builder.provider;

import java.util.Random;

import com.jparams.object.builder.provider.context.ProviderContext;

public class DoubleProvider implements Provider
{
    private final Random random;

    public DoubleProvider()
    {
        random = new Random();
    }

    @Override
    public Double provide(final ProviderContext providerContext)
    {
        return random.nextDouble();
    }

    @Override
    public boolean supports(final Class<?> clazz)
    {
        return clazz.isAssignableFrom(Double.class) || clazz.isAssignableFrom(double.class);
    }
}
