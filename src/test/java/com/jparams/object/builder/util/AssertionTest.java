package com.jparams.object.builder.util;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AssertionTest
{
    @Test
    public void ifNotNullReturnNonNull()
    {
        assertThat(Assertion.ifNotNull("abc")).isEqualTo("abc");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ifNotNullThrowsExceptionOnNull()
    {
        Assertion.ifNotNull(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void assertNotNullThrowsExceptionOnNull()
    {
        Assertion.assertNotNull(null);
    }

    @Test
    public void assertNotNullDoesNothingOnNonNull()
    {
        Assertion.assertNotNull("abc");
    }
}