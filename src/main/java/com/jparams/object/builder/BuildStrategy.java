package com.jparams.object.builder;

/**
 * Build strategy
 */
public enum BuildStrategy
{
    /**
     * Build an instance of an class using the best possible constructor
     */
    CONSTRUCTOR,

    /**
     * Build an instance of a class using field injection. This will bypass any validation logic in the constructor.
     */
    FIELD_INJECTION,

    /**
     * An attempt will be made to build the object by invoking the constructor, if that fails, field injection will be used as a fallback
     */
    AUTO
}
