package com.jparams.object.builder.provider;

import java.util.Arrays;
import java.util.function.Predicate;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.path.Path;
import com.jparams.object.builder.type.Type;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CachingProviderTest
{
    private CachingProvider subject;

    @Mock
    private Provider mockProvider1;

    @Mock
    private Provider mockProvider2;

    @Mock
    private Predicate<Type<?>> mockPredicate;

    @Before
    public void setUp()
    {
        subject = new CachingProvider(Arrays.asList(mockProvider1, mockProvider2), mockPredicate);
    }

    @Test
    public void testSupportsReturnsTrueIfAnyDelegateReturnsTrue()
    {
        when(mockProvider1.supports(any())).thenReturn(true);

        final Type<String> type = Type.forClass(String.class);
        final boolean supports = subject.supports(type);

        assertThat(supports).isTrue();

        verify(mockProvider1).supports(type);
        verifyNoMoreInteractions(mockProvider2);
    }

    @Test
    public void testSupportsReturnsFalseIfNoDelegateReturnsTrue()
    {
        when(mockProvider1.supports(any())).thenReturn(false);
        when(mockProvider2.supports(any())).thenReturn(false);

        final Type<String> type = Type.forClass(String.class);
        final boolean supports = subject.supports(type);

        assertThat(supports).isFalse();

        verify(mockProvider1).supports(type);
        verify(mockProvider2).supports(type);
    }

    @Test
    public void testProvideCachedValue()
    {
        when(mockProvider1.supports(any())).thenReturn(true);
        when(mockProvider1.provide(any())).thenReturn("abc");
        when(mockPredicate.test(any())).thenReturn(true);

        final Context mockContext = mockContextForType(String.class);
        final Object provided = subject.provide(mockContext);
        assertThat(provided).isEqualTo("abc");

        final Object providedAgain = subject.provide(mockContext);
        assertThat(providedAgain).isEqualTo("abc");

        verify(mockProvider1).supports(Type.forClass(String.class));
        verify(mockProvider1, times(1)).provide(mockContext);
        verifyNoMoreInteractions(mockProvider2);
    }

    @Test
    public void testProvideDoesNotProvideCachedValue()
    {
        when(mockProvider1.supports(any())).thenReturn(true);
        when(mockProvider1.provide(any())).thenReturn("abc").thenReturn("def");
        when(mockPredicate.test(any())).thenReturn(false);

        final Context mockContext = mockContextForType(String.class);
        final Object provided = subject.provide(mockContext);
        assertThat(provided).isEqualTo("abc");

        final Object providedAgain = subject.provide(mockContext);
        assertThat(providedAgain).isEqualTo("def");

        verify(mockProvider1, times(2)).supports(Type.forClass(String.class));
        verify(mockProvider1, times(2)).provide(mockContext);
        verifyNoMoreInteractions(mockProvider2);
    }

    private static Context mockContextForType(final Class<?> clazz)
    {
        final Context mockContext = mock(Context.class);
        when(mockContext.getPath()).thenReturn(new Path("", clazz == null ? null : Type.forClass(clazz), null));
        return mockContext;
    }
}