package com.jparams.object.builder.provider;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

import com.jparams.object.builder.provider.context.ProviderContext;

public class SetProvider implements Provider
{
    private final Random random = new Random();

    @Override
    public boolean supports(final Class<?> clazz)
    {
        return clazz.isAssignableFrom(Set.class);
    }

    @Override
    public Set<?> provide(final ProviderContext context)
    {
        if (context.getPath().getGenerics().isEmpty())
        {
            return Collections.emptySet();
        }

        final Set<Object> list = new LinkedHashSet<>();
        final Class<?> type = context.getPath().getGenerics().get(0);

        for (int i = 0; i < randomSize(); i++)
        {
            final Object child = context.createChild("[" + i + "]", type);
            list.add(child);
        }

        return list;
    }

    private int randomSize()
    {
        final int size = random.nextInt(5);
        return size > 0 ? size : 1;
    }
}
