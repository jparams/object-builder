package com.jparams.object.builder.provider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.jparams.object.builder.path.PathFactory;
import com.jparams.object.builder.provider.context.ProviderContext;

public class ListProvider implements Provider
{
    private final Random random = new Random();

    @Override
    public List<?> provide(final ProviderContext providerContext)
    {
        if (path.getGenerics().isEmpty())
        {
            return Collections.emptyList();
        }

        final List<Object> list = new ArrayList<>();
        final Class<?> type = path.getGenerics().get(0);

        for (int i = 0; i < getListSize(); i++)
        {
            list.add(objectFactory.create(PathFactory.createPath("[" + i + "]", type, path)));
        }

        return list;
    }

    private int getListSize()
    {
        final int size = random.nextInt(5);
        return size > 0 ? size : 1;
    }

    @Override
    public boolean supports(final Class<?> clazz)
    {
        return clazz.isAssignableFrom(List.class);
    }
}
