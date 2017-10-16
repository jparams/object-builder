package com.jparams.object.builder.provider;

import com.jparams.object.builder.provider.context.ProviderContext;

public interface Provider
{
    boolean supports(Class<?> clazz);

    Object provide(ProviderContext context);
}
