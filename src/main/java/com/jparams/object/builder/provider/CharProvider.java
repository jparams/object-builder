package com.jparams.object.builder.provider;

import java.util.UUID;

import com.jparams.object.builder.provider.context.ProviderContext;

public class CharProvider implements Provider
{
    @Override
    public boolean supports(final Class<?> clazz)
    {
        return clazz.isAssignableFrom(Character.class) || clazz.isAssignableFrom(char.class);
    }

    @Override
    public Character provide(final ProviderContext context)
    {
        return UUID.randomUUID().toString().charAt(0);
    }
}
