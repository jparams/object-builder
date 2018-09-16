package com.jparams.object.builder.provider;

import java.util.Random;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.type.Type;

public class ByteProvider implements Provider
{
    private final Random random = new Random();

    @Override
    public boolean supports(final Type<?> type)
    {
        return type.getJavaType().isAssignableFrom(Byte.class) || type.getJavaType().isAssignableFrom(byte.class);
    }

    @Override
    public Byte provide(final Context context)
    {
        return (byte) random.nextInt(Byte.MAX_VALUE);
    }
}
