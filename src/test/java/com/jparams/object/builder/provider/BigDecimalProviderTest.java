package com.jparams.object.builder.provider;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BigDecimalProviderTest
{
    private BigDecimalProvider subject;

    @Before
    public void setUp()
    {
        subject = new BigDecimalProvider();
    }

    @Test
    public void createsABigDecimal()
    {
        final BigDecimal bigDecimal = subject.provide(null);
        assertThat(bigDecimal).isNotNull();
    }

    @Test
    public void supportsBigDecimal()
    {
        assertThat(subject.supports(BigDecimal.class)).isTrue();
        assertThat(subject.supports(Double.class)).isFalse();
    }
}