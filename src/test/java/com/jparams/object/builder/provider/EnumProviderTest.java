package com.jparams.object.builder.provider;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.path.Path;
import com.jparams.object.builder.type.Type;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EnumProviderTest
{
    private Provider subject;

    @Before
    public void setUp()
    {
        subject = new EnumProvider();
    }

    @Test
    public void testSupports()
    {
        assertThat(subject.supports(Type.forClass(MyEnum1.class))).isTrue();
        assertThat(subject.supports(Type.forClass(MyEnum2.class))).isTrue();
        assertThat(subject.supports(Type.forClass(MyEnum3.class))).isTrue();
        assertThat(subject.supports(Type.forClass(Enum.class))).isFalse();
        assertThat(subject.supports(Type.forClass(String.class))).isFalse();
    }

    @Test
    public void testProvideNullOnNoPossibleValue()
    {
        assertThat(subject.provide(mockContextForType(MyEnum1.class))).isNull();
        assertThat(subject.provide(mockContextForType(MyEnum2.class))).isEqualTo(MyEnum2.VAL_1);
        assertThat(subject.provide(mockContextForType(MyEnum3.class))).isNotNull();
    }

    private static Context mockContextForType(final Class<?> clazz)
    {
        final Context mockContext = mock(Context.class);
        when(mockContext.getPath()).thenReturn(new Path("", clazz == null ? null : Type.forClass(clazz), null));
        return mockContext;
    }

    private enum MyEnum1
    {
    }

    private enum MyEnum2
    {
        VAL_1
    }

    @SuppressWarnings("unused")
    private enum MyEnum3
    {
        VAL_1,
        VAL_2
    }
}