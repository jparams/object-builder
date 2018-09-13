package com.jparams.object.builder.type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class TypeBuilder
{
    private final Class<?> clazz;
    private final List<Type> generics = new ArrayList<>();

    TypeBuilder(final Class<?> clazz)
    {
        this.clazz = clazz;
    }

    public TypeBuilder withGenerics(final Class<?>... generics)
    {
        return withGenerics(Stream.of(generics).map(clazz -> Type.forClass(clazz).build()).collect(Collectors.toList()));
    }

    public TypeBuilder withGenerics(final Type... generics)
    {
        return withGenerics(Arrays.asList(generics));
    }

    public TypeBuilder withGenerics(final List<Type> generics)
    {
        validateGenerics(generics);
        this.generics.clear();
        this.generics.addAll(generics);
        return this;
    }

    private void validateGenerics(final List<Type> generics)
    {
        final int expectedSize = clazz.getTypeParameters().length;

        if (generics.size() != expectedSize)
        {
            throw new IllegalArgumentException("Expected " + expectedSize + " generics, got " + generics.size());
        }
    }

    public Type build()
    {
        return new Type(clazz, generics);
    }
}
