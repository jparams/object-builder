package com.jparams.object.builder.provider;

import java.sql.Date;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.type.Type;

public class DateProvider implements Provider
{
    @Override
    public boolean supports(final Type<?> type)
    {
        return type.getJavaType().isAssignableFrom(Date.class);
    }

    @Override
    public Date provide(final Context context)
    {
        final long time = new java.util.Date().getTime();
        return new Date(time);
    }
}
