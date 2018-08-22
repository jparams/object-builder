package com.jparams.object.builder.provider;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.jparams.object.builder.Context;

public class ZonedDateTimeProvider implements Provider
{
    private static final ZoneId UTC = ZoneId.of("UTC");

    @Override
    public boolean supports(final Class<?> clazz)
    {
        return clazz.isAssignableFrom(ZonedDateTime.class);
    }

    @Override
    public ZonedDateTime provide(final Context context)
    {
        return ZonedDateTime.now(UTC);
    }
}
