package com.jparams.object.builder.provider;

import java.util.Random;

import com.jparams.object.builder.provider.context.ProviderContext;

public class BooleanProvider implements Provider
{
    private final Random random = new Random();

    @Override
    public Boolean provide(final ProviderContext providerContext)
    {
        return random.nextBoolean();
    }

    @Override
    public boolean supports(final Class<?> clazz)
    {
        return clazz.isAssignableFrom(Boolean.class) || clazz.isAssignableFrom(boolean.class);
    }
}
