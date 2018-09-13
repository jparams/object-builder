package com.jparams.object.builder.provider;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.type.Type;
import com.jparams.object.builder.type.TypeMap;

/**
 * Prefab value provider
 */
public class PrefabValueProvider implements Provider
{
    private final TypeMap<Object> prefabValueMap;

    public PrefabValueProvider(final TypeMap<Object> prefabValueMap)
    {
        this.prefabValueMap = prefabValueMap;
    }

    @Override
    public boolean supports(final Type type)
    {
        return prefabValueMap.findMatch(type).isPresent();
    }

    @Override
    public Object provide(final Context context)
    {
        return prefabValueMap.findMatch(context.getPath().getType()).orElse(null);
    }
}
