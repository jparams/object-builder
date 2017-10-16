package com.jparams.object.builder.provider;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringProviderTest
{
    private StringProvider subject;

    @Before
    public void setUp()
    {
        subject = new StringProvider();
    }

    @Test
    public void createsAString()
    {
        final String string = subject.provide(null);
        assertThat(string).isNotNull();
    }

    @Test
    public void supportsString()
    {
        assertThat(subject.supports(String.class)).isTrue();
        assertThat(subject.supports(Double.class)).isFalse();
    }
}