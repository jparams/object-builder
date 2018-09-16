package com.jparams.object.builder.util;

/**
 * Assertion utility class
 */
public final class Assertion
{
    private Assertion()
    {
    }

    /**
     * Assert given object is not null
     *
     * @param value value to test
     * @param <T>   type
     * @return value
     */
    public static <T> T ifNotNull(final T value)
    {
        assertNotNull(value);
        return value;
    }

    /**
     * Assert the given value is not null
     *
     * @param value value to test
     */
    public static void assertNotNull(final Object value)
    {
        if (value == null)
        {
            throw new IllegalArgumentException("Unexpected null value");
        }
    }
}
