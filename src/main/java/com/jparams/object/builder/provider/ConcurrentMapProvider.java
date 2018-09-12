package com.jparams.object.builder.provider;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.type.MemberType;

public class ConcurrentMapProvider implements Provider
{
    @Override
    public boolean supports(final Class<?> clazz)
    {
        return clazz.isAssignableFrom(ConcurrentMap.class);
    }

    @Override
    public ConcurrentMap<?, ?> provide(final Context context)
    {
        final ConcurrentMap<Object, Object> map = new ConcurrentHashMap<>();

        if (context.getPath().getMemberType().getGenerics().size() < 2)
        {
            context.logWarning("No generics found. Could not populate Map");
            return map;
        }

        final MemberType keyType = context.getPath().getMemberType().getGenerics().get(0);
        final MemberType valueType = context.getPath().getMemberType().getGenerics().get(1);
        final Object key = context.createChild("[0.key]", keyType);
        final Object value = context.createChild("[0.value]", valueType);
        map.put(key, value);
        return map;
    }
}
