package com.jparams.object.builder.provider;

import com.jparams.object.builder.type.Type;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringProviderTest
{
    private Provider subject;

    @Before
    public void setUp()
    {
        subject = new StringProvider();
    }

    @Test
    public void testSupports()
    {
        assertThat(subject.supports(Type.forClass(String.class))).isTrue();
        assertThat(subject.supports(Type.forClass(CharSequence.class))).isTrue();
        assertThat(subject.supports(Type.forClass(Integer.class))).isFalse();
    }

    @Test
    public void testProvide()
    {
        assertThat(subject.provide(null)).isInstanceOf(String.class);
    }
}