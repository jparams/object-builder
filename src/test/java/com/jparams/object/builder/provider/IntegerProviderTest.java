package com.jparams.object.builder.provider;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IntegerProviderTest
{
    private IntegerProvider subject;

    @Before
    public void setUp()
    {
        subject = new IntegerProvider();
    }

    @Test
    public void createsAInteger()
    {
        final Integer integer = subject.provide(null);
        assertThat(integer).isNotNull();
    }

    @Test
    public void supportsInteger()
    {
        assertThat(subject.supports(int.class)).isTrue();
        assertThat(subject.supports(Integer.class)).isTrue();
        assertThat(subject.supports(Double.class)).isFalse();
    }
}