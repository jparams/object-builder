package com.jparams.object.builder.provider;

import com.jparams.object.builder.provider.context.ProviderContext;

public class NullProvider implements Provider
{
    @Override
    public Object provide(final ProviderContext providerContext)
    {
        if (path.getType() == byte.class)
        {
            return (byte) 0;
        }

        if (path.getType() == short.class)
        {
            return (short) 0;
        }

        if (path.getType() == int.class)
        {
            return 0;
        }

        if (path.getType() == long.class)
        {
            return 0L;
        }

        if (path.getType() == float.class)
        {
            return 0.0f;
        }

        if (path.getType() == double.class)
        {
            return 0.0d;
        }

        if (path.getType() == boolean.class)
        {
            return false;
        }

        if (path.getType() == char.class)
        {
            return '\u0000';
        }

        return null;
    }

    @Override
    public boolean supports(final Class<?> clazz)
    {
        return true;
    }
}
