package com.jparams.object.builder.provider;

import java.time.LocalDateTime;

import com.jparams.object.builder.provider.context.ProviderContext;

public class LocalDateTimeProvider implements Provider
{
    @Override
    public LocalDateTime provide(final ProviderContext providerContext)
    {
        return LocalDateTime.now();
    }

    @Override
    public boolean supports(final Class<?> clazz)
    {
        return clazz.isAssignableFrom(LocalDateTime.class);
    }
}
