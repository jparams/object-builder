package com.jparams.object.builder.provider;

import java.util.Random;

import com.jparams.object.builder.provider.context.ProviderContext;

public class IntegerProvider implements Provider
{
    private final Random random;

    public IntegerProvider()
    {
        random = new Random();
    }

    @Override
    public Integer provide(final ProviderContext providerContext)
    {
        return random.nextInt();
    }

    @Override
    public boolean supports(final Class<?> clazz)
    {
        return clazz.isAssignableFrom(Integer.class) || clazz.isAssignableFrom(int.class);
    }
}
