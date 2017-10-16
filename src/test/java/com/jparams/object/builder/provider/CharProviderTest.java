package com.jparams.object.builder.provider;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CharProviderTest
{
    private CharProvider subject;

    @Before
    public void setUp()
    {
        subject = new CharProvider();
    }

    @Test
    public void createsAChar()
    {
        final Character character = subject.provide(null);
        assertThat(character).isNotNull();
    }

    @Test
    public void supportsChar()
    {
        assertThat(subject.supports(char.class)).isTrue();
        assertThat(subject.supports(Character.class)).isTrue();
        assertThat(subject.supports(Double.class)).isFalse();
    }
}