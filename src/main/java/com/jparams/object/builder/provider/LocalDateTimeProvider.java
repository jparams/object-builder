package com.jparams.object.builder.provider;

import java.time.LocalDateTime;

import com.jparams.object.builder.provider.context.ProviderContext;

public class LocalDateTimeProvider implements Provider
{
    @Override
    public boolean supports(final Class<?> clazz)
    {
        return clazz.isAssignableFrom(LocalDateTime.class);
    }

    @Override
    public LocalDateTime provide(final ProviderContext context)
    {
        return LocalDateTime.now();
    }
}
