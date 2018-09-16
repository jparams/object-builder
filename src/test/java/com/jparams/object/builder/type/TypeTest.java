package com.jparams.object.builder.type;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import static org.assertj.core.api.Assertions.assertThat;

public class TypeTest
{
    @Test
    public void testCreateForClass()
    {
        final Type<String> type = Type.forClass(String.class);
        assertThat(type.getJavaType()).isEqualTo(String.class);
        assertThat(type.getGenerics()).isEmpty();
    }

    @Test
    public void testCreateForClassWithMissingGenerics()
    {
        final Type<List> type = Type.forClass(List.class);
        assertThat(type.getJavaType()).isEqualTo(List.class);
        assertThat(type.getGenerics()).isEmpty();
    }

    @Test
    public void testCreateForClassWithGenerics()
    {
        final Type<List> type = Type.forClass(List.class).withGenerics(String.class);
        assertThat(type.getJavaType()).isEqualTo(List.class);
        assertThat(type.getGenerics()).containsExactly(Type.forClass(String.class));
    }

    @Test
    public void testCreateForClassWithGenericsWithinGenerics()
    {
        final Type<List> type = Type.forClass(List.class)
                                    .withGenerics(Type.forClass(List.class)
                                                      .withGenerics(String.class));

        assertThat(type.getJavaType()).isEqualTo(List.class);
        assertThat(type.getGenerics()).containsExactly(Type.forClass(List.class).withGenerics(String.class));
    }

    @Test
    public void testCreateForClassWithMultipleGenerics()
    {
        final Type<Map> type = Type.forClass(Map.class).withGenerics(Integer.class, String.class);
        assertThat(type.getJavaType()).isEqualTo(Map.class);
        assertThat(type.getGenerics()).containsExactly(Type.forClass(Integer.class), Type.forClass(String.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateForClassWithTooManyGenerics()
    {
        Type.forClass(Map.class).withGenerics(Integer.class, String.class, String.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateForClassWithTooFewGenerics()
    {
        Type.forClass(Map.class).withGenerics(Integer.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateForClassWithExplicitlyNoGenerics()
    {
        Type.forClass(Map.class).withGenerics(Collections.emptyList());
    }

    @Test
    public void testEqualsAndHashCode()
    {
        EqualsVerifier.forClass(Type.class)
                      .usingGetClass()
                      .withPrefabValues(Type.class, Type.forClass(String.class), Type.forClass(Integer.class))
                      .verify();
    }
}