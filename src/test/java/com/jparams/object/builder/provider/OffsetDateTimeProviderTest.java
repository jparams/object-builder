package com.jparams.object.builder.provider;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import com.jparams.object.builder.type.Type;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OffsetDateTimeProviderTest
{
    private Provider subject;

    @Before
    public void setUp()
    {
        subject = new OffsetDateTimeProvider();
    }

    @Test
    public void testSupports()
    {
        assertThat(subject.supports(Type.forClass(OffsetDateTime.class).build())).isTrue();
        assertThat(subject.supports(Type.forClass(LocalDateTime.class).build())).isFalse();
    }

    @Test
    public void testProvide()
    {
        assertThat(subject.provide(null)).isInstanceOf(OffsetDateTime.class);
    }
}