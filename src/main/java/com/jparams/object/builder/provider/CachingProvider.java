package com.jparams.object.builder.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.type.Type;

/**
 * Caches and returns the same value of a known {@link Type}
 */
public class CachingProvider implements Provider
{
    private final Map<Type, Object> cache = new HashMap<>();
    private final List<Provider> providers;
    private final Predicate<Type> cachePredicate;

    public CachingProvider(final List<Provider> providers, final Predicate<Type> cachePredicate)
    {
        this.providers = new ArrayList<>(providers);
        this.cachePredicate = cachePredicate;
    }

    @Override
    public synchronized Object provide(final Context context)
    {
        final Type memberType = context.getPath().getType();

        if (!cachePredicate.test(memberType))
        {
            return getObject(context, memberType);
        }

        if (cache.containsKey(memberType))
        {
            return cache.get(memberType);
        }

        final Object value = getObject(context, memberType);
        cache.put(memberType, value);
        return value;
    }

    @Override
    public boolean supports(final Type type)
    {
        return findSupportingProvider(type).isPresent();
    }

    private Object getObject(final Context context, final Type type)
    {
        return findSupportingProvider(type).map(provider -> provider.provide(context)).orElse(null);
    }

    private Optional<Provider> findSupportingProvider(final Type type)
    {
        return providers.stream()
                        .filter(provider -> provider.supports(type))
                        .findFirst();
    }
}
