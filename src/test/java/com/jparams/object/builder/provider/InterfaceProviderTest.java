package com.jparams.object.builder.provider;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.model.MyInterface;
import com.jparams.object.builder.path.Path;
import com.jparams.object.builder.type.Type;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class InterfaceProviderTest
{
    private InterfaceProvider subject;

    @Before
    public void setUp()
    {
        subject = new InterfaceProvider();
    }

    @Test
    public void testSupports()
    {
        assertThat(subject.supports(Type.forClass(MyInterface.class).build())).isTrue();
        assertThat(subject.supports(Type.forClass(String.class).build())).isFalse();
    }

    @Test
    public void testProvide()
    {
        final Context mockContext = mock(Context.class);
        when(mockContext.getPath()).thenReturn(new Path("", Type.forClass(MyInterface.class).build(), null));
        when(mockContext.createChild(any(), eq(Type.forClass(String.class).build()))).thenReturn("abc");

        final MyInterface provided = (MyInterface) subject.provide(mockContext);
        assertThat(provided.generateString()).isEqualTo("abc");
        assertThat(provided.generateString()).isEqualTo("abc");

        verify(mockContext, times(1)).createChild(any(), eq(Type.forClass(String.class).build()));
    }

    @Test
    public void testProvideHandlesVoid()
    {
        final Context mockContext = mock(Context.class);
        when(mockContext.getPath()).thenReturn(new Path("", Type.forClass(MyInterface.class).build(), null));

        final MyInterface provided = (MyInterface) subject.provide(mockContext);
        provided.voidMethod();
    }
}