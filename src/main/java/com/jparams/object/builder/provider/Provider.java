package com.jparams.object.builder.provider;

import com.jparams.object.builder.Context;

public interface Provider
{
    boolean supports(Class<?> clazz);

    Object provide(Context context);
}
