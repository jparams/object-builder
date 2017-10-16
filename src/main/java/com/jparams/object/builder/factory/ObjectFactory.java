package com.jparams.object.builder.factory;

import java.util.List;

import com.jparams.object.builder.path.Path;
import com.jparams.object.builder.path.PathFilter;
import com.jparams.object.builder.provider.Provider;

public class ObjectFactory
{
    private final List<Provider> providers;
    private final PathFilter pathFilter;
    private final Provider nullProvider;
    private final int maxDepth;

    public ObjectFactory(final List<Provider> providers,
                         final Provider nullProvider,
                         final PathFilter pathFilter,
                         final int maxDepth)
    {
        this.providers = providers;
        this.nullProvider = nullProvider;
        this.pathFilter = pathFilter;
        this.maxDepth = maxDepth;
    }

    @SuppressWarnings("unchecked")
    public <T> T create(final Path path)
    {
        if (path.getDepth() > maxDepth || !pathFilter.accept(path))
        {
            return (T) nullProvider.provide(path);
        }

        return (T) providers.stream()
                            .filter(provider -> provider.supports(path.getType()))
                            .map(provider -> provider.provide(path))
                            .findFirst()
                            .orElse(null);
    }
}
