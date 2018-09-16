package com.jparams.object.builder.provider;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import com.jparams.object.builder.type.Type;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ZonedDateTimeProviderTest
{
    private Provider subject;

    @Before
    public void setUp()
    {
        subject = new ZonedDateTimeProvider();
    }

    @Test
    public void testSupports()
    {
        assertThat(subject.supports(Type.forClass(ZonedDateTime.class))).isTrue();
        assertThat(subject.supports(Type.forClass(LocalDateTime.class))).isFalse();
    }

    @Test
    public void testProvide()
    {
        assertThat(subject.provide(null)).isInstanceOf(ZonedDateTime.class);
    }
}