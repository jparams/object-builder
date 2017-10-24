package com.jparams.object.builder.provider;

import java.util.UUID;

import com.jparams.object.builder.Context;

public class StringProvider implements Provider
{
    @Override
    public boolean supports(final Class<?> clazz)
    {
        return clazz.isAssignableFrom(String.class);
    }

    @Override
    public String provide(final Context context)
    {
        return UUID.randomUUID().toString();
    }
}
