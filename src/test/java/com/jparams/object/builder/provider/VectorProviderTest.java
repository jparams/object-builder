package com.jparams.object.builder.provider;

import java.util.Set;
import java.util.Vector;

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

public class VectorProviderTest
{
    private VectorProvider subject;

    @Before
    public void setUp()
    {
        subject = new VectorProvider();
    }

    @Test
    public void testSupports()
    {
        assertThat(subject.supports(Type.forClass(Vector.class))).isTrue();
        assertThat(subject.supports(Type.forClass(Set.class))).isFalse();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testProvideReturnsEmptyOnUnknownGeneric()
    {
        final Context mockContext = mock(Context.class);
        when(mockContext.getPath()).thenReturn(new Path("", Type.forClass(Vector.class), null));
        when(mockContext.createChild(any(), any())).thenReturn("abc");

        final Vector<?> provided = subject.provide(mockContext);

        assertThat(provided).isEmpty();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testProvide()
    {
        final Context mockContext = mock(Context.class);
        final Type<?> type = Type.forClass(Vector.class).withGenerics(String.class);
        when(mockContext.getPath()).thenReturn(new Path("", type, null));
        when(mockContext.createChild(any(), any())).thenReturn("abc");

        final Vector<?> provided = subject.provide(mockContext);

        assertThat((Vector) provided).containsExactly("abc");

        verify(mockContext).createChild("[0]", type.getGenerics().get(0).getType());
    }
}