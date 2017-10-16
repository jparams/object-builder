package com.jparams.object.builder.provider;

import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LocalTimeProviderTest
{
    private LocalTimeProvider subject;

    @Before
    public void setUp()
    {
        subject = new LocalTimeProvider();
    }

    @Test
    public void createsALocalTime()
    {
        final LocalTime localTime = subject.provide(null);
        assertThat(localTime).isNotNull();
    }

    @Test
    public void supportsLocalTime()
    {
        assertThat(subject.supports(LocalTime.class)).isTrue();
        assertThat(subject.supports(Double.class)).isFalse();
    }
}