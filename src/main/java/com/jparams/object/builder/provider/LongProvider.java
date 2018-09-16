package com.jparams.object.builder.provider;

import java.util.Random;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.type.Type;

public class LongProvider implements Provider
{
    private final Random random = new Random();

    @Override
    public boolean supports(final Type<?> type)
    {
        return type.getJavaType().isAssignableFrom(Long.class) || type.getJavaType().isAssignableFrom(long.class);
    }

    @Override
    public Long provide(final Context context)
    {
        return random.nextLong();
    }
}
