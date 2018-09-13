package com.jparams.object.builder.provider;

import java.time.LocalTime;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.type.Type;

public class LocalTimeProvider implements Provider
{
    @Override
    public boolean supports(final Type type)
    {
        return type.getJavaType().isAssignableFrom(LocalTime.class);
    }

    @Override
    public LocalTime provide(final Context context)
    {
        return LocalTime.now();
    }
}
