package com.jparams.object.builder.provider;

import java.util.Collections;
import java.util.Set;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.type.MemberType;

public class SetProvider implements Provider
{
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

        final MemberType memberType = context.getPath().getMemberType().getGenerics().get(0);
        return Collections.singleton(context.createChild("[0]", memberType));
    }
}
