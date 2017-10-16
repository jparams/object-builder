package com.jparams.object.builder.provider;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LocalDateTimeProviderTest
{
    private LocalDateTimeProvider subject;

    @Before
    public void setUp()
    {
        subject = new LocalDateTimeProvider();
    }

    @Test
    public void createsALocalDateTime()
    {
        final LocalDateTime localDateTime = subject.provide(null);
        assertThat(localDateTime).isNotNull();
    }

    @Test
    public void supportsLocalDateTime()
    {
        assertThat(subject.supports(LocalDateTime.class)).isTrue();
        assertThat(subject.supports(Double.class)).isFalse();
    }
}