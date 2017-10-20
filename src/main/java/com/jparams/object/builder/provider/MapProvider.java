package com.jparams.object.builder.provider;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.jparams.object.builder.provider.context.ProviderContext;

public class MapProvider implements Provider
{
    private final Random random = new Random();

    @Override
    public boolean supports(final Class<?> clazz)
    {
        return clazz.isAssignableFrom(Map.class);
    }

    @Override
    public Map<?, ?> provide(final ProviderContext context)
    {
        if (context.getPath().getGenerics().size() < 2)
        {
            return Collections.emptyMap();
        }

        final Map<Object, Object> map = new HashMap<>();
        final Class<?> keyType = context.getPath().getGenerics().get(0);
        final Class<?> valueType = context.getPath().getGenerics().get(1);

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
