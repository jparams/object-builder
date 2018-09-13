package com.jparams.object.builder.provider;

import java.time.LocalDate;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.type.Type;

public class LocalDateProvider implements Provider
{
    @Override
    public boolean supports(final Type type)
    {
        return type.getJavaType().isAssignableFrom(LocalDate.class);
    }

    @Override
    public LocalDate provide(final Context context)
    {
        return LocalDate.now();
    }
}
