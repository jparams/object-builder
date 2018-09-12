package com.jparams.object.builder.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.type.MemberType;

/**
 * Caches and returns the same value of a known {@link MemberType}
 */
public class CachingProvider implements Provider
{
    private final Map<MemberType, Object> cache = new HashMap<>();
    private final List<Provider> providers;
    private final Predicate<MemberType> cachePredicate;

    public CachingProvider(final List<Provider> providers, final Predicate<MemberType> cachePredicate)
    {
        this.providers = new ArrayList<>(providers);
        this.cachePredicate = cachePredicate;
    }

    @Override
    public synchronized Object provide(final Context context)
    {
        final MemberType memberType = context.getPath().getMemberType();

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
    public boolean supports(final Class<?> clazz)
    {
        return findSupportingProvider(clazz).isPresent();
    }

    private Object getObject(final Context context, final MemberType memberType)
    {
        return findSupportingProvider(memberType.getType()).map(provider -> provider.provide(context)).orElse(null);
    }

    private Optional<Provider> findSupportingProvider(final Class<?> clazz)
    {
        return providers.stream()
                        .filter(provider -> provider.supports(clazz))
                        .findFirst();
    }
}
