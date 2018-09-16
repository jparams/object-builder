package com.jparams.object.builder.provider;

import java.util.ArrayDeque;
import java.util.Queue;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.type.Type;

public class QueueProvider implements Provider
{
    @Override
    public boolean supports(final Type<?> type)
    {
        return type.getJavaType().isAssignableFrom(Queue.class);
    }

    @Override
    public Queue<?> provide(final Context context)
    {
        final Queue<Object> queue = new ArrayDeque<>();

        if (context.getPath().getType().getGenerics().isEmpty())
        {
            context.logWarning("No generics found. Could not populate List");
            return queue;
        }

        final Type<?> type = context.getPath().getType().getGenerics().get(0);
        queue.add(context.createChild("[0]", type));
        return queue;
    }
}
