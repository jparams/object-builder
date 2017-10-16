package com.jparams.object.builder.provider;

import java.math.BigDecimal;
import java.util.Random;

import com.jparams.object.builder.provider.context.ProviderContext;

public class BigDecimalProvider implements Provider
{
    private final Random random = new Random();

    @Override
    public boolean supports(final Class<?> clazz)
    {
        return clazz.isAssignableFrom(BigDecimal.class);
    }

    @Override
    public BigDecimal provide(final ProviderContext context)
    {
        return BigDecimal.valueOf(random.nextDouble());
    }
}
