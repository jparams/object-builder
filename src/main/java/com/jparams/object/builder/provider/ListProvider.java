package com.jparams.object.builder.provider;

import java.util.Collections;
import java.util.List;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.type.Type;

public class ListProvider implements Provider
{
    @Override
    public boolean supports(final Type type)
    {
        return type.getJavaType().isAssignableFrom(List.class);
    }

    @Override
    public List<?> provide(final Context context)
    {
        if (context.getPath().getType().getGenerics().isEmpty())
        {
            context.logWarning("No generics found. Could not populate List");
            return Collections.emptyList();
        }

        final Type memberType = context.getPath().getType().getGenerics().get(0);
        final Object child = context.createChild("[0]", memberType);
        return Collections.singletonList(child);
    }
}
