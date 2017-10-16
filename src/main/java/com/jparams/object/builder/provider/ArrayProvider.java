package com.jparams.object.builder.provider;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.jparams.object.builder.path.Path;
import com.jparams.object.builder.path.PathFactory;
import com.jparams.object.builder.provider.context.ProviderContext;

public class ArrayProvider implements Provider
{
    private final Random random = new Random();

    @Override
    public Object[] provide(final ProviderContext providerContext)
    {
        final List<Object> list = new ArrayList<>();
        final int randomArraySize = calculateRandomArraySize();

        for (int i = 0; i < randomArraySize; i++)
        {
            final Path itemPath = PathFactory.createPath("[" + i + "]", path.getType().getComponentType(), path);
            final Object item = objectFactory.create(itemPath);
            list.add(item);
        }

        final Object[] emptyArray = (Object[]) Array.newInstance(path.getType().getComponentType(), list.size());
        return list.toArray(emptyArray);
    }

    private int calculateRandomArraySize()
    {
        final int size = random.nextInt(5);
        return size > 0 ? size : 1;
    }

    @Override
    public boolean supports(final Class<?> clazz)
    {
        return clazz.isArray();
    }
}
