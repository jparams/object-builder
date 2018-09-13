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
        assertThat(subject.supports(Type.forClass(String.class).build())).isTrue();
        assertThat(subject.supports(Type.forClass(Integer.class).build())).isFalse();
    }

    @Test
    public void testProvide()
    {
        assertThat(subject.provide(null)).isInstanceOf(String.class);
    }
}