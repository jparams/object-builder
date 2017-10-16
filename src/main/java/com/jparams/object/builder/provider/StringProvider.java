package com.jparams.object.builder.provider;

import java.util.UUID;

import com.jparams.object.builder.provider.context.ProviderContext;

public class StringProvider implements Provider
{
    @Override
    public String provide(final ProviderContext providerContext)
    {
        return UUID.randomUUID().toString();
    }

    @Override
    public boolean supports(final Class<?> clazz)
    {
        return clazz.isAssignableFrom(String.class);
    }
}
