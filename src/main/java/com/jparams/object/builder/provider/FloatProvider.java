package com.jparams.object.builder.provider;

import java.util.Random;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.type.Type;

public class FloatProvider implements Provider
{
    private final Random random = new Random();

    @Override
    public boolean supports(final Type<?> type)
    {
        return type.getJavaType().isAssignableFrom(Float.class) || type.getJavaType().isAssignableFrom(float.class);
    }

    @Override
    public Float provide(final Context context)
    {
        return random.nextFloat();
    }
}
