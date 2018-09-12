package com.jparams.object.builder.provider;

/**
 * Implementation of {@link Provider} that supports all value types
 */
public abstract class AllValueTypeProvider implements Provider
{
    @Override
    public final boolean supports(final Class<?> clazz)
    {
        return true;
    }
}
