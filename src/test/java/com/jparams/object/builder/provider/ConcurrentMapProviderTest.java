package com.jparams.object.builder.provider;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.path.Path;
import com.jparams.object.builder.type.Type;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ConcurrentMapProviderTest
{
    private ConcurrentMapProvider subject;

    @Before
    public void setUp()
    {
        subject = new ConcurrentMapProvider();
    }

    @Test
    public void testSupports()
    {
        assertThat(subject.supports(Type.forClass(ConcurrentMap.class).build())).isTrue();
        assertThat(subject.supports(Type.forClass(List.class).build())).isFalse();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testProvideReturnsEmptyOnUnknownGeneric()
    {
        final Context mockContext = mock(Context.class);
        final Type type = Type.forClass(ConcurrentMap.class).build();
        when(mockContext.getPath()).thenReturn(new Path("", type, null));
        when(mockContext.createChild(any(), any())).thenReturn("abc");

        final ConcurrentMap<?, ?> provided = subject.provide(mockContext);

        assertThat(provided).isEmpty();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testProvide()
    {
        final Context mockContext = mock(Context.class);
        final Type type = Type.forClass(ConcurrentMap.class).withGenerics(String.class, String.class).build();
        when(mockContext.getPath()).thenReturn(new Path("", type, null));
        when(mockContext.createChild(any(), any())).thenReturn("abc");

        final ConcurrentMap<?, ?> provided = subject.provide(mockContext);

        assertThat((ConcurrentMap) provided).containsEntry("abc", "abc");

        verify(mockContext).createChild("[0.key]", type.getGenerics().get(0));
        verify(mockContext).createChild("[0.value]", type.getGenerics().get(1));

    }
}