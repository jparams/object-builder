//
// Copyright (c) 2018 Resonate Group Ltd.  All Rights Reserved.
//

package com.jparams.object.builder.provider;

import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.type.MemberType;

public class SortedSetProvider implements Provider
{
    private final Random random = new Random();

    @Override
    public boolean supports(final Class<?> clazz)
    {
        return clazz.isAssignableFrom(SortedSet.class);
    }

    @Override
    public SortedSet<?> provide(final Context context)
    {
        if (context.getPath().getMemberType().getGenerics().isEmpty())
        {
            context.logWarning("No generics found. Could not populate Set");
            return new TreeSet<>();
        }

        final SortedSet<Object> list = new TreeSet<>();
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
