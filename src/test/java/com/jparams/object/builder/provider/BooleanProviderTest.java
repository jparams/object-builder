package com.jparams.object.builder.provider;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BooleanProviderTest
{
    private BooleanProvider subject;

    @Before
    public void setUp()
    {
        subject = new BooleanProvider();
    }

    @Test
    public void createsABoolean()
    {
        final Boolean bool = subject.provide(null);
        assertThat(bool).isNotNull();
    }

    @Test
    public void supportsBoolean()
    {
        assertThat(subject.supports(boolean.class)).isTrue();
        assertThat(subject.supports(Boolean.class)).isTrue();
        assertThat(subject.supports(Double.class)).isFalse();
    }
}