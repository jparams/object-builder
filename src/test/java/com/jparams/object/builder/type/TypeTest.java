package com.jparams.object.builder.type;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class TypeTest
{
    @Test
    public void testEqualsAndHashCode()
    {
        EqualsVerifier.forClass(Type.class)
                      .usingGetClass()
                      .withPrefabValues(Type.class, Type.forClass(String.class).build(), Type.forClass(Integer.class).build())
                      .verify();
    }
}