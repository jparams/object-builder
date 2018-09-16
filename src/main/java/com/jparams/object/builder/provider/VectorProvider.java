package com.jparams.object.builder.provider;

import java.util.Vector;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.type.Type;

public class VectorProvider implements Provider
{
    @Override
    public boolean supports(final Type<?> type)
    {
        return type.getJavaType().isAssignableFrom(Vector.class);
    }

    @Override
    public Vector<?> provide(final Context context)
    {
        final Vector<Object> vector = new Vector<>();

        if (context.getPath().getType().getGenerics().isEmpty())
        {
            context.logWarning("No generics found. Could not populate List");
            return vector;
        }

        final Type<?> type = context.getPath().getType().getGenerics().get(0);
        vector.add(context.createChild("[0]", type));
        return vector;
    }
}
