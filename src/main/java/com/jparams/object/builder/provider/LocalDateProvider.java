package com.jparams.object.builder.provider;

import java.time.LocalDate;

import com.jparams.object.builder.provider.context.ProviderContext;

public class LocalDateProvider implements Provider
{
    @Override
    public LocalDate provide(final ProviderContext providerContext)
    {
        return LocalDate.now();
    }

    @Override
    public boolean supports(final Class<?> clazz)
    {
        return clazz.isAssignableFrom(LocalDate.class);
    }
}
