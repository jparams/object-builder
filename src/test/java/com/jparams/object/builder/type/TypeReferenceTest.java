package com.jparams.object.builder.type;

import java.util.List;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TypeReferenceTest
{
    @Test
    public void testResolveType()
    {
        final TypeReference<List<List<String>>> typeReference = new TypeReference<List<List<String>>>()
        {
        };

        assertThat(typeReference.getType()).isEqualTo(Type.forClass(List.class).withGenerics(Type.forClass(List.class).withGenerics(String.class)));
    }

    @Test
    public void testResolveTypeOfUnknownType()
    {
        final TypeReference typeReference = new TypeReference()
        {
        };

        assertThat(typeReference.getType()).isNull();
    }
}