package com.jparams.object.builder.provider;

import java.util.UUID;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.type.Type;

public class StringProvider implements Provider
{
    @Override
    public boolean supports(final Type<?> type)
    {
        return type.getJavaType().isAssignableFrom(String.class);
    }

    @Override
    public String provide(final Context context)
    {
        return UUID.randomUUID().toString();
    }
}
