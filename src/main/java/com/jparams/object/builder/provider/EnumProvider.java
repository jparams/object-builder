package com.jparams.object.builder.provider;

import java.util.Random;

import com.jparams.object.builder.provider.context.ProviderContext;

public class EnumProvider implements Provider
{
    private final Random random;

    public EnumProvider()
    {
        random = new Random();
    }

    @Override
    public Enum provide(final ProviderContext providerContext)
    {
        final Class<?> type = path.getType();
        final int valueCount = type.getEnumConstants().length;

        if (valueCount > 0)
        {
            final int index = random.nextInt(valueCount - 1);
            return (Enum<?>) type.getEnumConstants()[index];
        }

        return null;
    }

    @Override
    public boolean supports(final Class<?> clazz)
    {
        return clazz.isEnum();
    }
}
