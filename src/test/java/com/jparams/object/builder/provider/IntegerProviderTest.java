package com.jparams.object.builder.provider;

import com.jparams.object.builder.type.Type;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IntegerProviderTest
{
    private Provider subject;

    @Before
    public void setUp()
    {
        subject = new IntegerProvider();
    }

    @Test
    public void testSupports()
    {
        assertThat(subject.supports(Type.forClass(Integer.class))).isTrue();
        assertThat(subject.supports(Type.forClass(int.class))).isTrue();
        assertThat(subject.supports(Type.forClass(String.class))).isFalse();
    }

    @Test
    public void testProvide()
    {
        assertThat(subject.provide(null)).isInstanceOf(Integer.class);
    }
}