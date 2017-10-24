package com.jparams.object.builder.provider;

import java.time.LocalTime;

import com.jparams.object.builder.Context;

public class LocalTimeProvider implements Provider
{
    @Override
    public boolean supports(final Class<?> clazz)
    {
        return clazz.isAssignableFrom(LocalTime.class);
    }

    @Override
    public LocalTime provide(final Context context)
    {
        return LocalTime.now();
    }
}
