package com.jparams.object.builder.provider;

import com.jparams.object.builder.type.Type;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ShortProviderTest
{
    private Provider subject;

    @Before
    public void setUp()
    {
        subject = new ShortProvider();
    }

    @Test
    public void testSupports()
    {
        assertThat(subject.supports(Type.forClass(Short.class).build())).isTrue();
        assertThat(subject.supports(Type.forClass(short.class).build())).isTrue();
        assertThat(subject.supports(Type.forClass(Integer.class).build())).isFalse();
    }

    @Test
    public void testProvide()
    {
        assertThat(subject.provide(null)).isInstanceOf(Short.class);
    }
}