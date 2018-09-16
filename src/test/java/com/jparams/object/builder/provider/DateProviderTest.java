package com.jparams.object.builder.provider;

import java.time.LocalDateTime;
import java.util.Date;

import com.jparams.object.builder.type.Type;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DateProviderTest
{
    private Provider subject;

    @Before
    public void setUp()
    {
        subject = new DateProvider();
    }

    @Test
    public void testSupports()
    {
        assertThat(subject.supports(Type.forClass(Date.class))).isTrue();
        assertThat(subject.supports(Type.forClass(java.sql.Date.class))).isTrue();
        assertThat(subject.supports(Type.forClass(LocalDateTime.class))).isFalse();
    }

    @Test
    public void testProvide()
    {
        assertThat(subject.provide(null)).isInstanceOf(Date.class).isInstanceOf(java.sql.Date.class);
    }
}