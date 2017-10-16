package com.jparams.object.builder.provider;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DateProviderTest
{
    private DateProvider subject;

    @Before
    public void setUp()
    {
        subject = new DateProvider();
    }

    @Test
    public void createsADate()
    {
        final Date date = subject.provide(null);
        assertThat(date).isNotNull();
    }

    @Test
    public void supportsDate()
    {
        assertThat(subject.supports(Date.class)).isTrue();
        assertThat(subject.supports(Double.class)).isFalse();
    }
}