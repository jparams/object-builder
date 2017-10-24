package com.jparams.object.builder.provider;

import java.time.LocalDate;

import com.jparams.object.builder.Context;

public class LocalDateProvider implements Provider
{
    @Override
    public boolean supports(final Class<?> clazz)
    {
        return clazz.isAssignableFrom(LocalDate.class);
    }

    @Override
    public LocalDate provide(final Context context)
    {
        return LocalDate.now();
    }
}
