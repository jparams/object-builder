package com.jparams.object.builder.provider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.type.Type;
import com.jparams.object.builder.utils.CollectionUtils;

/**
 * Caches and returns the same value of a known {@link Type}
 */
public class CachingProvider implements Provider
{
    private final Map<Type, Object> cache = new HashMap<>();
    private final List<Provider> providers;
    private final Predicate<Type<?>> cachePredicate;

    public CachingProvider(final List<Provider> providers, final Predicate<Type<?>> cachePredicate)
    {
        this.providers = CollectionUtils.unmodifiableCopy(providers);
        this.cachePredicate = cachePredicate;
    }

    @Override
    public synchronized Object provide(final Context context)
    {
        final Type<?> type = context.getPath().getType();

        if (!cachePredicate.test(type))
        {
            return getObject(context, type);
        }

        if (cache.containsKey(type))
        {
            return cache.get(type);
        }

        final Object value = getObject(context, type);
        cache.put(type, value);
        return value;
    }

    @Override
    public boolean supports(final Type<?> type)
    {
        return findSupportingProvider(type).isPresent();
    }

    private Object getObject(final Context context, final Type<?> type)
    {
        return findSupportingProvider(type).map(provider -> provider.provide(context)).orElse(null);
    }

    private Optional<Provider> findSupportingProvider(final Type<?> type)
    {
        return providers.stream()
                        .filter(provider -> provider.supports(type))
                        .findFirst();
    }
}
