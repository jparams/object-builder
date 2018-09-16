package com.jparams.object.builder.provider;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.type.Type;

/**
 * Value provider
 */
public interface Provider
{
    /**
     * Return true if the given class is supported by this provider.
     *
     * @param type class to test
     * @return true if supported
     */
    boolean supports(Type<?> type);

    /**
     * Provide a generated value in given context
     *
     * @param context context
     * @return generated value
     */
    Object provide(Context context);
}
