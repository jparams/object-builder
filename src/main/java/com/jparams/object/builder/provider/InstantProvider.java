package com.jparams.object.builder.provider;

import java.time.Instant;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.type.Type;

public class InstantProvider implements Provider
{
    @Override
    public boolean supports(final Type<?> type)
    {
        return type.getJavaType().isAssignableFrom(Instant.class);
    }

    @Override
    public Instant provide(final Context context)
    {
        return Instant.now();
    }
}
