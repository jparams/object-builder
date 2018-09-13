package com.jparams.object.builder.provider;

import java.util.Random;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.type.Type;

public class ShortProvider implements Provider
{
    private final Random random = new Random();

    @Override
    public boolean supports(final Type type)
    {
        return type.getJavaType().isAssignableFrom(Short.class) || type.getJavaType().isAssignableFrom(short.class);
    }

    @Override
    public Short provide(final Context context)
    {
        return (short) random.nextInt(Short.MAX_VALUE);
    }
}
