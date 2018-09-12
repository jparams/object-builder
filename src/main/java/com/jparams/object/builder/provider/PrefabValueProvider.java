package com.jparams.object.builder.provider;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.jparams.object.builder.Context;

/**
 * Prefab value provider
 */
public class PrefabValueProvider implements Provider
{
    private final Map<Class<?>, Object> prefabValueMap;

    public PrefabValueProvider(final Map<Class<?>, Object> prefabValueMap)
    {
        this.prefabValueMap = prefabValueMap == null ? Collections.emptyMap() : Collections.unmodifiableMap(new HashMap<>(prefabValueMap));
    }

    @Override
    public boolean supports(final Class<?> clazz)
    {
        return prefabValueMap.containsKey(clazz);
    }

    @Override
    public Object provide(final Context context)
    {
        final Class<?> type = context.getPath().getMemberType().getType();
        return prefabValueMap.get(type);
    }
}
