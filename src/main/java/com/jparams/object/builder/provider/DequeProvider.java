package com.jparams.object.builder.provider;

import java.util.ArrayDeque;
import java.util.Deque;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.type.Type;

public class DequeProvider implements Provider
{
    @Override
    public boolean supports(final Type type)
    {
        return type.getJavaType().isAssignableFrom(Deque.class);
    }

    @Override
    public Deque<?> provide(final Context context)
    {
        final Deque<Object> deque = new ArrayDeque<>();

        if (context.getPath().getType().getGenerics().isEmpty())
        {
            context.logWarning("No generics found. Could not populate List");
            return deque;
        }

        final Type memberType = context.getPath().getType().getGenerics().get(0);
        deque.add(context.createChild("[0]", memberType));
        return deque;
    }
}
