package com.jparams.object.builder.provider;

import java.lang.reflect.Array;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.type.MemberType;
import com.jparams.object.builder.type.MemberTypeResolver;

public class ArrayProvider implements Provider
{
    @Override
    public boolean supports(final Class<?> clazz)
    {
        return clazz.isArray();
    }

    @Override
    public Object[] provide(final Context context)
    {
        final Class<?> componentType = context.getPath().getMemberType().getType().getComponentType();
        final MemberType memberType = MemberTypeResolver.resolve(componentType);
        final Object[] array = (Object[]) Array.newInstance(componentType, 1);
        array[0] = context.createChild("[0]", memberType);
        return array;
    }
}
