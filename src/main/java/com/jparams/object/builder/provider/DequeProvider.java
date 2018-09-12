package com.jparams.object.builder.provider;

import java.util.ArrayDeque;
import java.util.Deque;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.type.MemberType;

public class DequeProvider implements Provider
{
    @Override
    public boolean supports(final Class<?> clazz)
    {
        return clazz.isAssignableFrom(Deque.class);
    }

    @Override
    public Deque<?> provide(final Context context)
    {
        final Deque<Object> deque = new ArrayDeque<>();

        if (context.getPath().getMemberType().getGenerics().isEmpty())
        {
            context.logWarning("No generics found. Could not populate List");
            return deque;
        }

        final MemberType memberType = context.getPath().getMemberType().getGenerics().get(0);
        deque.add(context.createChild("[0]", memberType));
        return deque;
    }
}
