package com.jparams.object.builder.provider.cache;

import java.util.ArrayList;
import java.util.List;

import com.jparams.object.builder.provider.Provider;
import com.jparams.object.builder.provider.context.ProviderContext;

public class CachedDataProvider implements Provider
{
    private final List<Provider> providers;

    public CachedDataProvider(final List<Provider> providers, final int cacheStart)
    {
        this.providers = new ArrayList<>(providers);
    }

    @Override
    public Object provide(final ProviderContext providerContext)
    {
        return null;
    }

    @Override
    public boolean supports(final Class<?> clazz)
    {
        return providers.stream().anyMatch(provider -> provider.supports(clazz));
    }
}
