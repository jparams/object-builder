package com.jparams.object.builder.provider;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.type.MemberType;

public class SetProvider implements Provider
{
    private final Random random = new Random();

    @Override
    public boolean supports(final Class<?> clazz)
    {
        return clazz.isAssignableFrom(Set.class);
    }

    @Override
    public Set<?> provide(final Context context)
    {
        if (context.getPath().getMemberType().getGenerics().isEmpty())
        {
            context.logWarning("No generics found. Could not populate Set");
            return Collections.emptySet();
        }

        final Set<Object> list = new LinkedHashSet<>();
        final MemberType memberType = context.getPath().getMemberType().getGenerics().get(0);

        for (int i = 0; i < randomSize(); i++)
        {
            final Object child = context.createChild("[" + i + "]", memberType);
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
