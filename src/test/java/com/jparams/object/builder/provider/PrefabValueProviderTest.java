package com.jparams.object.builder.provider;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.jparams.object.builder.Context;
import com.jparams.object.builder.path.Path;
import com.jparams.object.builder.type.Type;
import com.jparams.object.builder.type.TypeMap;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PrefabValueProviderTest
{
    private PrefabValueProvider subject;

    @Before
    public void setUp()
    {
        final TypeMap<Object> prefabValueMap = new TypeMap<>();
        prefabValueMap.put(Type.forClass(String.class), "abc");
        prefabValueMap.put(Type.forClass(List.class), Collections.emptyList());
        prefabValueMap.put(Type.forClass(List.class).withGenerics(String.class), Collections.singletonList("abc"));

        subject = new PrefabValueProvider(prefabValueMap);
    }

    @Test
    public void testSupports()
    {
        assertThat(subject.supports(Type.forClass(String.class))).isTrue();
        assertThat(subject.supports(Type.forClass(List.class))).isTrue();
        assertThat(subject.supports(Type.forClass(List.class).withGenerics(String.class))).isTrue();
        assertThat(subject.supports(Type.forClass(List.class).withGenerics(Integer.class))).isTrue();
        assertThat(subject.supports(Type.forClass(Set.class))).isFalse();
    }

    @Test
    public void testProvide()
    {
        final Context mockContext = mock(Context.class);
        when(mockContext.getPath()).thenReturn(new Path("", Type.forClass(String.class), null))
                                   .thenReturn(new Path("", Type.forClass(List.class), null))
                                   .thenReturn(new Path("", Type.forClass(List.class).withGenerics(String.class), null))
                                   .thenReturn(new Path("", Type.forClass(List.class).withGenerics(Integer.class), null))
                                   .thenReturn(new Path("", Type.forClass(Set.class), null));

        assertThat(subject.provide(mockContext)).isEqualTo("abc");
        assertThat(subject.provide(mockContext)).isEqualTo(Collections.emptyList());
        assertThat(subject.provide(mockContext)).isEqualTo(Collections.singletonList("abc"));
        assertThat(subject.provide(mockContext)).isEqualTo(Collections.emptyList());
        assertThat(subject.provide(mockContext)).isNull();
    }
}