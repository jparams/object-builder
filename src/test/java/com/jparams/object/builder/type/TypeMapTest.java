package com.jparams.object.builder.type;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TypeMapTest
{
    private TypeMap<String> subject;

    @Before
    public void setUp()
    {
        subject = new TypeMap<>();
    }

    @Test
    public void testExactMatch()
    {
        subject.put(Type.forClass(String.class).build(), "found!");

        final Optional<String> match = subject.findMatch(Type.forClass(String.class).build());

        assertThat(match).hasValue("found!");
    }

    @Test
    public void testExactMatchWithGenerics()
    {
        subject.put(Type.forClass(List.class).withGenerics(String.class).build(), "found!");

        final Optional<String> match = subject.findMatch(Type.forClass(List.class).withGenerics(String.class).build());

        assertThat(match).hasValue("found!");
    }

    @Test
    public void testBestMatchWithGenericsMissing()
    {
        subject.put(Type.forClass(List.class).build(), "found!");

        final Optional<String> match = subject.findMatch(Type.forClass(List.class).withGenerics(String.class).build());

        assertThat(match).hasValue("found!");
    }

    @Test
    public void testBestMatchWithGenericsMismatch()
    {
        subject.put(Type.forClass(List.class).withGenerics(Integer.class).build(), "found!");

        final Optional<String> match = subject.findMatch(Type.forClass(List.class).withGenerics(String.class).build());

        assertThat(match).isNotPresent();
    }

    @Test
    public void testNoMatch()
    {
        final Optional<String> match = subject.findMatch(Type.forClass(List.class).build());

        assertThat(match).isNotPresent();
    }
}