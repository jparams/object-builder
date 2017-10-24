package com.jparams.object.builder.provider;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.type.MemberType;

public class MapProvider implements Provider
{
    private final Random random = new Random();

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

        final Map<Object, Object> map = new HashMap<>();
        final MemberType keyType = context.getPath().getMemberType().getGenerics().get(0);
        final MemberType valueType = context.getPath().getMemberType().getGenerics().get(1);

        for (int i = 0; i < randomSize(); i++)
        {
            final Object key = context.createChild("[" + i + ".key]", keyType);
            final Object value = context.createChild("[" + i + ".value]", valueType);
            map.put(key, value);
        }

        return map;
    }

    private int randomSize()
    {
        final int size = random.nextInt(5);
        return size > 0 ? size : 1;
    }
}
