package com.jparams.object.builder.provider;

import java.util.Date;

import com.jparams.object.builder.provider.context.ProviderContext;

public class DateProvider implements Provider
{
    @Override
    public boolean supports(final Class<?> clazz)
    {
        return clazz.isAssignableFrom(Date.class);
    }

    @Override
    public Date provide(final ProviderContext context)
    {
        return new Date();
    }
}
