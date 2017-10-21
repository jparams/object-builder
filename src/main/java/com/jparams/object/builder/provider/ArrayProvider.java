package com.jparams.object.builder.provider;

import java.lang.reflect.Array;
import java.util.Random;

import com.jparams.object.builder.provider.context.ProviderContext;
import com.jparams.object.builder.type.MemberType;
import com.jparams.object.builder.type.MemberTypeResolver;

public class ArrayProvider implements Provider
{
    private final Random random = new Random();

    @Override
    public boolean supports(final Class<?> clazz)
    {
        return clazz.isArray();
    }

    @Override
    public Object[] provide(final ProviderContext context)
    {
        final Class<?> componentType = context.getPath().getMemberType().getType().getComponentType();
        final MemberType memberType = MemberTypeResolver.resolve(componentType);
        final Object[] array = (Object[]) Array.newInstance(componentType, randomSize());

        for (int i = 0; i < array.length; i++)
        {
            array[i] = context.createChild("[" + i + "]", memberType);
        }

        return array;
    }

    private int randomSize()
    {
        final int size = random.nextInt(5);
        return size > 0 ? size : 1;
    }
}
