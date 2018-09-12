package com.jparams.object.builder.provider;

import java.util.Collections;
import java.util.Deque;
import java.util.Set;

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

public class DequeProviderTest
{
    private DequeProvider subject;

    @Before
    public void setUp()
    {
        subject = new DequeProvider();
    }

    @Test
    public void testSupports()
    {
        assertThat(subject.supports(Deque.class)).isTrue();
        assertThat(subject.supports(Set.class)).isFalse();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testProvideReturnsEmptyOnUnknownGeneric()
    {
        final Context mockContext = mock(Context.class);
        when(mockContext.getPath()).thenReturn(new Path("", new MemberType(Deque.class), null));
        when(mockContext.createChild(any(), any())).thenReturn("abc");

        final Deque<?> provided = subject.provide(mockContext);

        assertThat(provided).isEmpty();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testProvide()
    {
        final Context mockContext = mock(Context.class);
        final MemberType genericType = new MemberType(String.class);
        when(mockContext.getPath()).thenReturn(new Path("", new MemberType(Deque.class, Collections.singletonList(genericType)), null));
        when(mockContext.createChild(any(), any())).thenReturn("abc");

        final Deque<?> provided = subject.provide(mockContext);

        assertThat((Deque) provided).containsExactly("abc");

        verify(mockContext).createChild("[0]", genericType);
    }
}