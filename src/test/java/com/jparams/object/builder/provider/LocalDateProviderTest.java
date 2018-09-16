package com.jparams.object.builder.provider;

import java.time.LocalDate;
import java.time.LocalTime;

import com.jparams.object.builder.type.Type;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LocalDateProviderTest
{
    private Provider subject;

    @Before
    public void setUp()
    {
        subject = new LocalDateProvider();
    }

    @Test
    public void testSupports()
    {
        assertThat(subject.supports(Type.forClass(LocalDate.class))).isTrue();
        assertThat(subject.supports(Type.forClass(LocalTime.class))).isFalse();
    }

    @Test
    public void testProvide()
    {
        assertThat(subject.provide(null)).isInstanceOf(LocalDate.class);
    }
}