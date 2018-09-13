package com.jparams.object.builder.type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represent a java class and its generics
 */
public class Type
{
    private final Class<?> javaType;
    private final List<Type> generics;

    Type(final Class<?> javaType, final List<Type> generics)
    {
        this.javaType = javaType;
        this.generics = generics == null ? Collections.emptyList() : Collections.unmodifiableList(new ArrayList<>(generics));
    }

    public Class<?> getJavaType()
    {
        return javaType;
    }

    public List<Type> getGenerics()
    {
        return generics;
    }

    /**
     * Build a new type for a given class
     *
     * @param clazz class to wrap
     * @return builder
     */
    public static TypeBuilder forClass(final Class<?> clazz)
    {
        return new TypeBuilder(clazz);
    }

    @Override
    public boolean equals(final Object other)
    {
        if (this == other)
        {
            return true;
        }

        if (other == null || getClass() != other.getClass())
        {
            return false;
        }

        final Type that = (Type) other;
        return Objects.equals(javaType, that.javaType) && Objects.equals(generics, that.generics);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(javaType, generics);
    }
}
