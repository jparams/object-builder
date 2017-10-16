package com.jparams.object.builder.provider;

import com.jparams.object.builder.provider.context.ProviderContext;

public interface Provider
{
    Object provide(ProviderContext providerContext);

    boolean supports(Class<?> clazz);
}
