package com.jparams.object.builder.provider;

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

public class ArrayProviderTest
{
    private ArrayProvider subject;

    @Before
    public void setUp()
    {
        subject = new ArrayProvider();
    }

    @Test
    public void testSupports()
    {
        assertThat(subject.supports(Integer[].class)).isTrue();
        assertThat(subject.supports(Set.class)).isFalse();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testProvide()
    {
        final Context mockContext = mock(Context.class);
        when(mockContext.getPath()).thenReturn(new Path("", new MemberType(Integer[].class), null));
        when(mockContext.createChild(any(), any())).thenReturn(1);

        final Object[] provided = subject.provide(mockContext);

        assertThat(provided).containsExactly(1);

        verify(mockContext).createChild("[0]", new MemberType(Integer.class));
    }
}