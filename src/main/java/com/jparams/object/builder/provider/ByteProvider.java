package com.jparams.object.builder.provider;

import java.util.Random;

import com.jparams.object.builder.Context;

public class ByteProvider implements Provider
{
    private final Random random = new Random();

    @Override
    public boolean supports(final Class<?> clazz)
    {
        return clazz.isAssignableFrom(Byte.class) || clazz.isAssignableFrom(byte.class);
    }

    @Override
    public Byte provide(final Context context)
    {
        return (byte) random.nextInt(Byte.MAX_VALUE);
    }
}
