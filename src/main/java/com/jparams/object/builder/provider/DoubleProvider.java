package com.jparams.object.builder.provider;

import java.util.Random;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.type.Type;

public class DoubleProvider implements Provider
{
    private final Random random = new Random();

    @Override
    public boolean supports(final Type<?> type)
    {
        return type.getJavaType().isAssignableFrom(Double.class) || type.getJavaType().isAssignableFrom(double.class);
    }

    @Override
    public Double provide(final Context context)
    {
        return random.nextDouble();
    }

}
