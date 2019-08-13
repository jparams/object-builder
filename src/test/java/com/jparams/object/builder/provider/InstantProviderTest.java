package com.jparams.object.builder.provider;

import java.time.Instant;

import com.jparams.object.builder.type.Type;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for {@link InstantProvider}
 */
public class InstantProviderTest
{
    private Provider subject;

    @Before
    public void setUp()
    {
        subject = new InstantProvider();
    }

    @Test
    public void testSupports()
    {
        assertThat(subject.supports(Type.forClass(Instant.class))).isTrue();
        assertThat(subject.supports(Type.forClass(String.class))).isFalse();
    }

    @Test
    public void testProvide()
    {
        assertThat(subject.provide(null)).isInstanceOf(Instant.class);
    }
}