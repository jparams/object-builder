package com.jparams.object.builder.provider;

import java.time.LocalDateTime;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.type.Type;

public class LocalDateTimeProvider implements Provider
{
    @Override
    public boolean supports(final Type type)
    {
        return type.getJavaType().isAssignableFrom(LocalDateTime.class);
    }

    @Override
    public LocalDateTime provide(final Context context)
    {
        return LocalDateTime.now();
    }
}
