package com.jparams.object.builder.provider;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.type.Type;

public class ZonedDateTimeProvider implements Provider
{
    private static final ZoneId UTC = ZoneId.of("UTC");

    @Override
    public boolean supports(final Type<?> type)
    {
        return type.getJavaType().isAssignableFrom(ZonedDateTime.class);
    }

    @Override
    public ZonedDateTime provide(final Context context)
    {
        return ZonedDateTime.now(UTC);
    }
}
