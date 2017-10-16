package com.jparams.object.builder.provider;

import java.time.LocalTime;

import com.jparams.object.builder.provider.context.ProviderContext;

public class LocalTimeProvider implements Provider
{
    @Override
    public LocalTime provide(final ProviderContext providerContext)
    {
        return LocalTime.now();
    }

    @Override
    public boolean supports(final Class<?> clazz)
    {
        return clazz.isAssignableFrom(LocalTime.class);
    }
}
