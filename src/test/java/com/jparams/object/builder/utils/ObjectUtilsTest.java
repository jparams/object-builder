package com.jparams.object.builder.utils;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ObjectUtilsTest
{
    @Test
    public void testCreateInstance()
    {
        final MyClass instance = ObjectUtils.createInstance(MyClass.class);
        assertThat(instance).isNotNull();
    }

    @SuppressWarnings("unused")
    private static class MyClass extends MyParentClass
    {
        private static Integer staticField1;

        private final String field1;

        public MyClass()
        {
            throw new UnsupportedOperationException();
        }
    }

    @SuppressWarnings("unused")
    private static class MyParentClass
    {
        private final String field2;

        MyParentClass()
        {
            throw new UnsupportedOperationException();
        }
    }
}