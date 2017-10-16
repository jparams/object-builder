package com.jparams.object.builder.provider;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LongProviderTest
{
    private LongProvider subject;

    @Before
    public void setUp()
    {
        subject = new LongProvider();
    }

    @Test
    public void createsALong()
    {
        final Long longNum = subject.provide(null);
        assertThat(longNum).isNotNull();
    }

    @Test
    public void supportsLong()
    {
        assertThat(subject.supports(long.class)).isTrue();
        assertThat(subject.supports(Long.class)).isTrue();
        assertThat(subject.supports(Double.class)).isFalse();
    }
}