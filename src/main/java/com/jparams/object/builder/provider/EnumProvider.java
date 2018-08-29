package com.jparams.object.builder.provider;

import java.util.Random;

import com.jparams.object.builder.Context;

public class EnumProvider implements Provider
{
    private final Random random = new Random();

    @Override
    public boolean supports(final Class<?> clazz)
    {
        return clazz.isEnum();
    }

    @Override
    public Enum provide(final Context context)
    {
        final Class<?> type = context.getPath().getMemberType().getType();
        final int valueCount = type.getEnumConstants().length;

        if (valueCount > 0)
        {
            final int index = random.nextInt(valueCount);
            return (Enum<?>) type.getEnumConstants()[index];
        }

        return null;
    }

}
