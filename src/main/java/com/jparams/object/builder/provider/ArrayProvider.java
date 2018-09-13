package com.jparams.object.builder.provider;

import java.lang.reflect.Array;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.type.Type;
import com.jparams.object.builder.type.TypeResolver;

public class ArrayProvider implements Provider
{
    @Override
    public boolean supports(final Type type)
    {
        return type.getJavaType().isArray();
    }

    @Override
    public Object[] provide(final Context context)
    {
        final Class<?> componentType = context.getPath().getType().getJavaType().getComponentType();
        final Type memberType = TypeResolver.resolve(componentType);
        final Object[] array = (Object[]) Array.newInstance(componentType, 1);
        array[0] = context.createChild("[0]", memberType);
        return array;
    }
}
