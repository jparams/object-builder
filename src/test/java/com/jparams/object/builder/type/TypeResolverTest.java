package com.jparams.object.builder.type;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TypeResolverTest
{
    @Test
    public void testResolveMethod() throws NoSuchMethodException
    {
        final Method method = DummyClass.class.getDeclaredMethod("getStringList");
        final Type<?> type = TypeResolver.resolveReturnType(null, method);
        assertThat(type).isEqualTo(Type.forClass(List.class).withGenerics(Type.forClass(Collection.class).withGenerics(String.class)));
    }

    @Test
    public void testResolveNullOnVoidMethod() throws NoSuchMethodException
    {
        final Method method = DummyClass.class.getDeclaredMethod("myVoidMethod", List.class);
        final Type<?> type = TypeResolver.resolveReturnType(null, method);
        assertThat(type).isNull();
    }

    @Test
    public void testResolveField() throws NoSuchFieldException
    {
        final Field field = DummyClass.class.getDeclaredField("stringList");
        final Type<?> type = TypeResolver.resolveFieldType(null, field);
        assertThat(type).isEqualTo(Type.forClass(List.class).withGenerics(Type.forClass(Collection.class).withGenerics(String.class)));
    }

    @Test
    public void testResolveParameter() throws NoSuchMethodException
    {
        final Method method = DummyClass.class.getDeclaredMethod("myVoidMethod", List.class);
        final Type<?> type = TypeResolver.resolveParameterType(null, method.getParameters()[0]);
        assertThat(type).isEqualTo(Type.forClass(List.class).withGenerics(Type.forClass(Collection.class).withGenerics(String.class)));
    }

    @Test
    public void testResolveClass()
    {
        final Type<?> type = TypeResolver.resolveType(String.class);
        assertThat(type).isEqualTo(Type.forClass(String.class));
    }

    @SuppressWarnings("unchecked")
    private static class DummyClass
    {
        List<Collection<String>> stringList = Collections.emptyList();

        List<Collection<String>> getStringList()
        {
            return stringList;
        }

        void myVoidMethod(final List<Collection<String>> stringList)
        {
            this.stringList = stringList;
        }
    }
}