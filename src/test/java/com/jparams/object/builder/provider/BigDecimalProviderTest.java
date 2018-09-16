package com.jparams.object.builder.provider;

import java.math.BigDecimal;

import com.jparams.object.builder.type.Type;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BigDecimalProviderTest
{
    private Provider subject;

    @Before
    public void setUp()
    {
        subject = new BigDecimalProvider();
    }

    @Test
    public void testSupports()
    {
        assertThat(subject.supports(Type.forClass(BigDecimal.class))).isTrue();
        assertThat(subject.supports(Type.forClass(String.class))).isFalse();
    }

    @Test
    public void testProvide()
    {
        assertThat(subject.provide(null)).isInstanceOf(BigDecimal.class);
    }
}