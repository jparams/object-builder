package com.jparams.object.builder.provider;

import java.time.OffsetDateTime;
import java.time.ZoneId;

import com.jparams.object.builder.Context;

public class OffsetDateTimeProvider implements Provider
{
    private static final ZoneId UTC = ZoneId.of("UTC");

    @Override
    public boolean supports(final Class<?> clazz)
    {
        return clazz.isAssignableFrom(OffsetDateTime.class);
    }

    @Override
    public OffsetDateTime provide(final Context context)
    {
        return OffsetDateTime.now(UTC);
    }
}
