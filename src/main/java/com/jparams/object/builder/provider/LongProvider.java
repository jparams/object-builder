package com.jparams.object.builder.provider;

import java.util.Random;

import com.jparams.object.builder.provider.context.ProviderContext;

public class LongProvider implements Provider
{
    private final Random random;

    public LongProvider()
    {
        random = new Random();
    }

    @Override
    public Long provide(final ProviderContext providerContext)
    {
        return random.nextLong();
    }

    @Override
    public boolean supports(final Class<?> clazz)
    {
        return clazz.isAssignableFrom(Long.class) || clazz.isAssignableFrom(long.class);
    }
}
