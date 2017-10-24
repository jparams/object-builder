package com.jparams.object.builder.provider;

import com.jparams.object.builder.Context;

public class NullProvider implements Provider
{
    @Override
    public boolean supports(final Class<?> clazz)
    {
        return true;
    }

    @Override
    public Object provide(final Context context)
    {
        final Class<?> type = context.getPath().getMemberType().getType();

        if (type == byte.class)
        {
            return (byte) 0;
        }

        if (type == short.class)
        {
            return (short) 0;
        }

        if (type == int.class)
        {
            return 0;
        }

        if (type == long.class)
        {
            return 0L;
        }

        if (type == float.class)
        {
            return 0.0f;
        }

        if (type == double.class)
        {
            return 0.0d;
        }

        if (type == boolean.class)
        {
            return false;
        }

        if (type == char.class)
        {
            return '\u0000';
        }

        return null;
    }
}
