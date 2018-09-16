package com.jparams.object.builder.provider;

import com.jparams.object.builder.type.Type;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ByteProviderTest
{
    private Provider subject;

    @Before
    public void setUp()
    {
        subject = new ByteProvider();
    }

    @Test
    public void testSupports()
    {
        assertThat(subject.supports(Type.forClass(Byte.class))).isTrue();
        assertThat(subject.supports(Type.forClass(byte.class))).isTrue();
        assertThat(subject.supports(Type.forClass(String.class))).isFalse();
    }

    @Test
    public void testProvide()
    {
        assertThat(subject.provide(null)).isInstanceOf(Byte.class);
    }
}