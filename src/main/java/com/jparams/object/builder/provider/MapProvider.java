package com.jparams.object.builder.provider;

import java.util.Collections;
import java.util.Map;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.type.MemberType;

public class MapProvider implements Provider
{
    @Override
    public boolean supports(final Class<?> clazz)
    {
        return clazz.isAssignableFrom(Map.class);
    }

    @Override
    public Map<?, ?> provide(final Context context)
    {
        if (context.getPath().getMemberType().getGenerics().size() < 2)
        {
            context.logWarning("No generics found. Could not populate Map");
            return Collections.emptyMap();
        }

        final MemberType keyType = context.getPath().getMemberType().getGenerics().get(0);
        final MemberType valueType = context.getPath().getMemberType().getGenerics().get(1);
        final Object key = context.createChild("[0.key]", keyType);
        final Object value = context.createChild("[0.value]", valueType);
        return Collections.singletonMap(key, value);
    }
}
