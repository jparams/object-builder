package com.jparams.object.builder.provider;

import java.util.Random;

import com.jparams.object.builder.provider.context.ProviderContext;

public class ShortProvider implements Provider
{
    private final Random random;

    public ShortProvider()
    {
        random = new Random();
    }

    @Override
    public Short provide(final ProviderContext providerContext)
    {
        return (short) random.nextInt(Short.MAX_VALUE);
    }

    @Override
    public boolean supports(final Class<?> clazz)
    {
        return clazz.isAssignableFrom(Short.class) || clazz.isAssignableFrom(short.class);
    }
}
