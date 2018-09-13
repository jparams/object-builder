package com.jparams.object.builder.provider;

import java.util.Date;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.type.Type;

public class DateProvider implements Provider
{
    @Override
    public boolean supports(final Type type)
    {
        return type.getJavaType().isAssignableFrom(Date.class);
    }

    @Override
    public Date provide(final Context context)
    {
        return new Date();
    }
}
