package com.jparams.object.builder.provider;

import java.util.Random;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.type.Type;

public class BooleanProvider implements Provider
{
    private final Random random = new Random();

    @Override
    public boolean supports(final Type type)
    {
        return type.getJavaType().isAssignableFrom(Boolean.class) || type.getJavaType().isAssignableFrom(boolean.class);
    }

    @Override
    public Boolean provide(final Context context)
    {
        return random.nextBoolean();
    }
}
