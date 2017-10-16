package com.jparams.object.builder.provider;

import java.math.BigDecimal;

import com.jparams.object.builder.factory.ObjectFactory;
import com.jparams.object.builder.path.Path;
import com.jparams.object.builder.path.PathFactory;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ArrayProviderTest
{
    private ArrayProvider subject;
    private ObjectFactory mockObjectFactory;

    @Before
    public void setUp()
    {
        mockObjectFactory = mock(ObjectFactory.class);
        subject = new ArrayProvider();
    }

    @Test
    public void createsArrayWithValues()
    {
        when(mockObjectFactory.create(any(Path.class))).thenReturn("abc");

        final String[] array = (String[]) subject.provide(PathFactory.createRootPath(String[].class));

        assertThat(array).isNotEmpty();
        assertThat(array).contains("abc");
    }

    @Test
    public void supportsArrayTypes()
    {
        assertThat(subject.supports(String[].class)).isTrue();
        assertThat(subject.supports(int[].class)).isTrue();
        assertThat(subject.supports(BigDecimal[].class)).isTrue();

        assertThat(subject.supports(String.class)).isFalse();
        assertThat(subject.supports(int.class)).isFalse();
        assertThat(subject.supports(BigDecimal.class)).isFalse();
    }
}