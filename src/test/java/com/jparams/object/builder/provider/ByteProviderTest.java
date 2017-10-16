package com.jparams.object.builder.provider;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ByteProviderTest
{
    private ByteProvider subject;

    @Before
    public void setUp()
    {
        subject = new ByteProvider();
    }

    @Test
    public void createsAByte()
    {
        final Byte aByte = subject.provide(null);
        assertThat(aByte).isNotNull();
    }

    @Test
    public void supportsByte()
    {
        assertThat(subject.supports(Byte.class)).isTrue();
        assertThat(subject.supports(Double.class)).isFalse();
    }
}