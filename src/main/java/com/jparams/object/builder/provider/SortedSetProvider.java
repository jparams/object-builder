package com.jparams.object.builder.provider;

import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.type.Type;

public class SortedSetProvider implements Provider
{
    @Override
    public boolean supports(final Type type)
    {
        return type.getJavaType().isAssignableFrom(SortedSet.class);
    }

    @Override
    public SortedSet<?> provide(final Context context)
    {
        if (context.getPath().getType().getGenerics().isEmpty())
        {
            context.logWarning("No generics found. Could not populate Set");
            return new TreeSet<>();
        }

        final Type memberType = context.getPath().getType().getGenerics().get(0);
        final Object child = context.createChild("[0]", memberType);
        return new TreeSet<>(Collections.singletonList(child));
    }
}
