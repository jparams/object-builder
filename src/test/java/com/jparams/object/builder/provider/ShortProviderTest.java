package com.jparams.object.builder.provider;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ShortProviderTest
{
    private ShortProvider subject;

    @Before
    public void setUp()
    {
        subject = new ShortProvider();
    }

    @Test
    public void createsAShort()
    {
        final Short shortNum = subject.provide(null);
        assertThat(shortNum).isNotNull();
    }

    @Test
    public void supportsShort()
    {
        assertThat(subject.supports(short.class)).isTrue();
        assertThat(subject.supports(Short.class)).isTrue();
        assertThat(subject.supports(Double.class)).isFalse();
    }
}