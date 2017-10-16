package com.jparams.object.builder.provider;

import java.util.Random;

import com.jparams.object.builder.provider.context.ProviderContext;

public class ByteProvider implements Provider
{
    private final Random random = new Random();

    @Override
    public Byte provide(final ProviderContext providerContext)
    {
        return (byte) random.nextInt(Byte.MAX_VALUE);
    }

    @Override
    public boolean supports(final Class<?> clazz)
    {
        return clazz.isAssignableFrom(Byte.class) || clazz.isAssignableFrom(byte.class);
    }
}
