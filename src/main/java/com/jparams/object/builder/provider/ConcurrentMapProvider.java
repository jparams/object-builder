package com.jparams.object.builder.provider;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.type.Type;

public class ConcurrentMapProvider implements Provider
{
    @Override
    public boolean supports(final Type type)
    {
        return type.getJavaType().isAssignableFrom(ConcurrentMap.class);
    }

    @Override
    public ConcurrentMap<?, ?> provide(final Context context)
    {
        final ConcurrentMap<Object, Object> map = new ConcurrentHashMap<>();

        if (context.getPath().getType().getGenerics().size() < 2)
        {
            context.logWarning("No generics found. Could not populate Map");
            return map;
        }

        final Type keyType = context.getPath().getType().getGenerics().get(0);
        final Type valueType = context.getPath().getType().getGenerics().get(1);
        final Object key = context.createChild("[0.key]", keyType);
        final Object value = context.createChild("[0.value]", valueType);
        map.put(key, value);
        return map;
    }
}
