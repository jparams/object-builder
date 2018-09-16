package com.jparams.object.builder.provider;

import java.util.Collections;
import java.util.Set;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.type.Type;

public class SetProvider implements Provider
{
    @Override
    public boolean supports(final Type<?> type)
    {
        return type.getJavaType().isAssignableFrom(Set.class);
    }

    @Override
    public Set<?> provide(final Context context)
    {
        if (context.getPath().getType().getGenerics().isEmpty())
        {
            context.logWarning("No generics found. Could not populate Set");
            return Collections.emptySet();
        }

        final Type<?> type = context.getPath().getType().getGenerics().get(0);
        return Collections.singleton(context.createChild("[0]", type));
    }
}
