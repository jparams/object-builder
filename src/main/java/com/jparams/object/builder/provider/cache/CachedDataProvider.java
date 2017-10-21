package com.jparams.object.builder.provider.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.jparams.object.builder.path.Path;
import com.jparams.object.builder.provider.Provider;
import com.jparams.object.builder.provider.context.ProviderContext;

public class CachedDataProvider implements Provider
{
    private final Map<Path, Object> cache = new HashMap<>();
    private final List<Provider> providers;
    private final int cacheStart;

    public CachedDataProvider(final List<Provider> providers, final int cacheStart)
    {
        this.providers = new ArrayList<>(providers);
        this.cacheStart = cacheStart;
    }

    @Override
    public Object provide(final ProviderContext context)
    {
        if (!cache.containsKey(context.getPath()) || context.getPath().getDepth() < cacheStart)
        {
            final Optional<Provider> provider = findSupportingProvider(context.getPath().getMemberType().getType());

            if (provider.isPresent())
            {
                final Object obj = provider.get().provide(context);
                cache.put(context.getPath(), obj);
                return obj;
            }

            return null;
        }

        return cache.get(context.getPath());
    }

    @Override
    public boolean supports(final Class<?> clazz)
    {
        return findSupportingProvider(clazz).isPresent();
    }

    private Optional<Provider> findSupportingProvider(final Class<?> clazz)
    {
        return providers.stream()
                        .filter(provider -> provider.supports(clazz))
                        .findFirst();
    }
}
