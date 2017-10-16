package com.jparams.object.builder.provider;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DoubleProviderTest
{
    private DoubleProvider subject;

    @Before
    public void setUp()
    {
        subject = new DoubleProvider();
    }

    @Test
    public void createsADouble()
    {
        final Double doubleVal = subject.provide(null);
        assertThat(doubleVal).isNotNull();
    }

    @Test
    public void supportsDouble()
    {
        assertThat(subject.supports(double.class)).isTrue();
        assertThat(subject.supports(Double.class)).isTrue();
        assertThat(subject.supports(Integer.class)).isFalse();
    }
}