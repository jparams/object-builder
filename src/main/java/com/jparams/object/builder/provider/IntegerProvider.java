package com.jparams.object.builder.provider;

import java.util.Random;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.type.Type;

public class IntegerProvider implements Provider
{
    private final Random random = new Random();

    @Override
    public boolean supports(final Type type)
    {
        return type.getJavaType().isAssignableFrom(Integer.class) || type.getJavaType().isAssignableFrom(int.class);
    }

    @Override
    public Integer provide(final Context context)
    {
        return random.nextInt();
    }
}
