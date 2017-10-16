package com.jparams.object.builder.provider;

import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.jparams.object.builder.path.PathFactory;
import com.jparams.object.builder.provider.context.ProviderContext;

public class SetProvider implements Provider
{
    private final Random random = new Random();

    @Override
    public Set<?> provide(final ProviderContext providerContext)
    {
        if (path.getGenerics().isEmpty())
        {
            return Collections.emptySet();
        }

        final Set<Object> set = new HashSet<>();
        final Class<?> type = path.getGenerics().get(0);

        for (int i = 0; i < getSetSize(); i++)
        {
            set.add(objectFactory.create(PathFactory.createPath("[" + i + "]", type, path)));
        }

        return set;
    }

    private int getSetSize()
    {
        final int size = random.nextInt(5);
        return size > 0 ? size : 1;
    }

    @Override
    public boolean supports(final Class<?> clazz)
    {
        return clazz.isAssignableFrom(Set.class);
    }
}
