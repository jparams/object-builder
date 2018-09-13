package com.jparams.object.builder.provider;

import com.jparams.object.builder.type.Type;

/**
 * Implementation of {@link Provider} that supports all value types
 */
public abstract class AnyValueTypeProvider implements Provider
{
    @Override
    public final boolean supports(final Type type)
    {
        return true;
    }
}
