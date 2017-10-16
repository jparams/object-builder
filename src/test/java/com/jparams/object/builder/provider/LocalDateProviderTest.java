package com.jparams.object.builder.provider;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LocalDateProviderTest
{
    private LocalDateProvider subject;

    @Before
    public void setUp()
    {
        subject = new LocalDateProvider();
    }

    @Test
    public void createsALocalDate()
    {
        final LocalDate localDate = subject.provide(null);
        assertThat(localDate).isNotNull();
    }

    @Test
    public void supportsLocalDate()
    {
        assertThat(subject.supports(LocalDate.class)).isTrue();
        assertThat(subject.supports(Double.class)).isFalse();
    }
}