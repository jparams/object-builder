package com.jparams.object.builder.provider;

import java.util.Vector;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.type.MemberType;

public class VectorProvider implements Provider
{
    @Override
    public boolean supports(final Class<?> clazz)
    {
        return clazz.isAssignableFrom(Vector.class);
    }

    @Override
    public Vector<?> provide(final Context context)
    {
        final Vector<Object> vector = new Vector<>();

        if (context.getPath().getMemberType().getGenerics().isEmpty())
        {
            context.logWarning("No generics found. Could not populate List");
            return vector;
        }

        final MemberType memberType = context.getPath().getMemberType().getGenerics().get(0);
        vector.add(context.createChild("[0]", memberType));
        return vector;
    }
}
