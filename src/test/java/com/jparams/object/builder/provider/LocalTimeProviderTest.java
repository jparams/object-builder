package com.jparams.object.builder.provider;

import java.time.LocalDateTime;
import java.time.LocalTime;

import com.jparams.object.builder.type.Type;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LocalTimeProviderTest
{
    private Provider subject;

    @Before
    public void setUp()
    {
        subject = new LocalTimeProvider();
    }

    @Test
    public void testSupports()
    {
        assertThat(subject.supports(Type.forClass(LocalTime.class))).isTrue();
        assertThat(subject.supports(Type.forClass(LocalDateTime.class))).isFalse();
    }

    @Test
    public void testProvide()
    {
        assertThat(subject.provide(null)).isInstanceOf(LocalTime.class);
    }
}