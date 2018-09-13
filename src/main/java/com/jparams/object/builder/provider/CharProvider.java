package com.jparams.object.builder.provider;

import java.util.UUID;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.type.Type;

public class CharProvider implements Provider
{
    @Override
    public boolean supports(final Type type)
    {
        return type.getJavaType().isAssignableFrom(Character.class) || type.getJavaType().isAssignableFrom(char.class);
    }

    @Override
    public Character provide(final Context context)
    {
        return UUID.randomUUID().toString().charAt(0);
    }
}
