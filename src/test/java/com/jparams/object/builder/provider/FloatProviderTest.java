package com.jparams.object.builder.provider;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FloatProviderTest
{
    private FloatProvider subject;

    @Before
    public void setUp()
    {
        subject = new FloatProvider();
    }

    @Test
    public void createsAFloat()
    {
        final Float Float = subject.provide(null);
        assertThat(Float).isNotNull();
    }

    @Test
    public void supportsFloat()
    {
        assertThat(subject.supports(float.class)).isTrue();
        assertThat(subject.supports(Float.class)).isTrue();
        assertThat(subject.supports(Double.class)).isFalse();
    }
}