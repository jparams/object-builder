package com.jparams.object.builder.provider;

import java.math.BigDecimal;
import java.util.Random;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.type.Type;

public class BigDecimalProvider implements Provider
{
    private final Random random = new Random();

    @Override
    public boolean supports(final Type type)
    {
        return type.getJavaType().isAssignableFrom(BigDecimal.class);
    }

    @Override
    public BigDecimal provide(final Context context)
    {
        return BigDecimal.valueOf(random.nextDouble());
    }
}
