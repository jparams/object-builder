package com.jparams.object.builder.utils;

import java.lang.reflect.Field;
import java.util.List;

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

    @Test
    public void testGetFields()
    {
        final List<Field> fields = ObjectUtils.getFields(MyClass.class);
        assertThat(fields).hasSize(2).extracting(Field::getName).containsExactly("field1", "field2");
    }

    @SuppressWarnings("unused")
    private static class MyClass extends MyParentClass
    {
        private static Integer staticField1;

        private String field1;

        public MyClass()
        {
            throw new UnsupportedOperationException();
        }
    }

    @SuppressWarnings("unused")
    private static class MyParentClass
    {
        private String field2;

        MyParentClass()
        {
            throw new UnsupportedOperationException();
        }
    }
}