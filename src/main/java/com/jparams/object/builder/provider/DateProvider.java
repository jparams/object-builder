package com.jparams.object.builder.provider;

import java.util.Date;

import com.jparams.object.builder.provider.context.ProviderContext;

public class DateProvider implements Provider
{
    @Override
    public Date provide(final ProviderContext providerContext)
    {
        return new Date();
    }

    @Override
    public boolean supports(final Class<?> clazz)
    {
        return clazz.isAssignableFrom(Date.class);
    }
}
