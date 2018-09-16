package com.jparams.object.builder.type;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TypeSetTest
{
    @Test
    public void testExactMatch()
    {
        final TypeSet subject = new TypeSet(Collections.singletonList(Type.forClass(String.class)));

        final boolean match = subject.contains(Type.forClass(String.class));

        assertThat(match).isTrue();
    }

    @Test
    public void testExactMatchWithGenerics()
    {
        final TypeSet subject = new TypeSet(Collections.singletonList(Type.forClass(List.class).withGenerics(String.class)));

        final boolean match = subject.contains(Type.forClass(List.class).withGenerics(String.class));

        assertThat(match).isTrue();
    }

    @Test
    public void testBestMatchWithGenericsMissing()
    {
        final TypeSet subject = new TypeSet(Collections.singletonList(Type.forClass(List.class)));

        final boolean match = subject.contains(Type.forClass(List.class).withGenerics(String.class));

        assertThat(match).isTrue();
    }

    @Test
    public void testBestMatchWithGenericsMismatch()
    {
        final TypeSet subject = new TypeSet(Collections.singletonList(Type.forClass(List.class).withGenerics(Integer.class)));

        final boolean match = subject.contains(Type.forClass(Collection.class).withGenerics(String.class));

        assertThat(match).isFalse();
    }

    @Test
    public void testNoMatch()
    {
        final TypeSet subject = new TypeSet(Collections.singletonList(Type.forClass(String.class)));

        final boolean match = subject.contains(Type.forClass(Integer.class));

        assertThat(match).isFalse();
    }
}