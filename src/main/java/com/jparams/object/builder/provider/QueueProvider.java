package com.jparams.object.builder.provider;

import java.util.ArrayDeque;
import java.util.Queue;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.type.MemberType;

public class QueueProvider implements Provider
{
    @Override
    public boolean supports(final Class<?> clazz)
    {
        return clazz.isAssignableFrom(Queue.class);
    }

    @Override
    public Queue<?> provide(final Context context)
    {
        final Queue<Object> queue = new ArrayDeque<>();

        if (context.getPath().getMemberType().getGenerics().isEmpty())
        {
            context.logWarning("No generics found. Could not populate List");
            return queue;
        }

        final MemberType memberType = context.getPath().getMemberType().getGenerics().get(0);
        queue.add(context.createChild("[0]", memberType));
        return queue;
    }
}
