package com.jparams.object.builder.provider;

import java.util.Collections;
import java.util.Map;

import com.jparams.object.builder.provider.context.ProviderContext;

public class MapProvider implements Provider
{
    @Override
    public Map<?, ?> provide(final ProviderContext providerContext)
    {
        return Collections.emptyMap();
    }

    @Override
    public boolean supports(final Class<?> clazz)
    {
        return clazz.isAssignableFrom(Map.class);
    }
}
