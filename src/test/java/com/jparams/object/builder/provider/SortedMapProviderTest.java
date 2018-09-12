package com.jparams.object.builder.provider;

import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.path.Path;
import com.jparams.object.builder.type.MemberType;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SortedMapProviderTest
{
    private SortedMapProvider subject;

    @Before
    public void setUp()
    {
        subject = new SortedMapProvider();
    }

    @Test
    public void testSupports()
    {
        assertThat(subject.supports(SortedMap.class)).isTrue();
        assertThat(subject.supports(List.class)).isFalse();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testProvideReturnsEmptyOnUnknownGeneric()
    {
        final Context mockContext = mock(Context.class);
        when(mockContext.getPath()).thenReturn(new Path("", new MemberType(SortedMap.class), null));
        when(mockContext.createChild(any(), any())).thenReturn("abc");

        final SortedMap<?, ?> provided = subject.provide(mockContext);

        assertThat(provided).isEmpty();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testProvide()
    {
        final Context mockContext = mock(Context.class);
        final MemberType keyGenericType = new MemberType(String.class);
        final MemberType valueGenericType = new MemberType(String.class);
        when(mockContext.getPath()).thenReturn(new Path("", new MemberType(SortedMap.class, Arrays.asList(keyGenericType, valueGenericType)), null));
        when(mockContext.createChild(any(), any())).thenReturn("abc");

        final SortedMap<?, ?> provided = subject.provide(mockContext);

        assertThat((SortedMap) provided).containsEntry("abc", "abc");

        verify(mockContext).createChild("[0.key]", keyGenericType);
        verify(mockContext).createChild("[0.value]", valueGenericType);

    }
}