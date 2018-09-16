package com.jparams.object.builder.provider;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.path.Path;
import com.jparams.object.builder.type.Type;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NullProviderTest
{
    private NullProvider subject;

    @Before
    public void setUp()
    {
        subject = new NullProvider();
    }

    @Test
    public void testSupportsAllType()
    {
        assertThat(subject.supports(null)).isTrue();
    }

    @Test
    public void testProvideProvidesNullOnUnknownType()
    {
        final Context context = mockContextForType(null);
        final Object provided = subject.provide(context);
        assertThat(provided).isNull();
    }

    @Test
    public void testProvideProvidesNullEquivalentForBytePrimitive()
    {
        final Context context = mockContextForType(byte.class);
        final Object provided = subject.provide(context);
        assertThat(provided).isEqualTo((byte) 0);
    }

    @Test
    public void testProvideProvidesNullEquivalentForShortPrimitive()
    {
        final Context context = mockContextForType(short.class);
        final Object provided = subject.provide(context);
        assertThat(provided).isEqualTo((short) 0);
    }

    @Test
    public void testProvideProvidesNullEquivalentForIntPrimitive()
    {
        final Context context = mockContextForType(int.class);
        final Object provided = subject.provide(context);
        assertThat(provided).isEqualTo(0);
    }

    @Test
    public void testProvideProvidesNullEquivalentForLongPrimitive()
    {
        final Context context = mockContextForType(long.class);
        final Object provided = subject.provide(context);
        assertThat(provided).isEqualTo((long) 0);
    }

    @Test
    public void testProvideProvidesNullEquivalentForFloatPrimitive()
    {
        final Context context = mockContextForType(float.class);
        final Object provided = subject.provide(context);
        assertThat(provided).isEqualTo((float) 0.0);
    }

    @Test
    public void testProvideProvidesNullEquivalentForDoublePrimitive()
    {
        final Context context = mockContextForType(double.class);
        final Object provided = subject.provide(context);
        assertThat(provided).isEqualTo(0.0);
    }

    @Test
    public void testProvideProvidesNullEquivalentForBooleanPrimitive()
    {
        final Context context = mockContextForType(boolean.class);
        final Object provided = subject.provide(context);
        assertThat(provided).isEqualTo(false);
    }

    @Test
    public void testProvideProvidesNullEquivalentForCharPrimitive()
    {
        final Context context = mockContextForType(char.class);
        final Object provided = subject.provide(context);
        assertThat(provided).isEqualTo('\u0000');
    }

    @Test
    public void testProvideProvidesNullForAnyObjectType()
    {
        final Context context = mockContextForType(String.class);
        final Object provided = subject.provide(context);
        assertThat(provided).isNull();
    }

    private static Context mockContextForType(final Class<?> clazz)
    {
        final Context mockContext = mock(Context.class);
        when(mockContext.getPath()).thenReturn(new Path("", clazz == null ? null : Type.forClass(clazz), null));
        return mockContext;
    }
}