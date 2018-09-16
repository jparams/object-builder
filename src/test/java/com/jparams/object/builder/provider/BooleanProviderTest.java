package com.jparams.object.builder.provider;

import com.jparams.object.builder.type.Type;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BooleanProviderTest
{
    private Provider subject;

    @Before
    public void setUp()
    {
        subject = new BooleanProvider();
    }

    @Test
    public void testSupports()
    {
        assertThat(subject.supports(Type.forClass(Boolean.class))).isTrue();
        assertThat(subject.supports(Type.forClass(boolean.class))).isTrue();
        assertThat(subject.supports(Type.forClass(String.class))).isFalse();
    }

    @Test
    public void testProvide()
    {
        assertThat(subject.provide(null)).isInstanceOf(Boolean.class);
    }
}