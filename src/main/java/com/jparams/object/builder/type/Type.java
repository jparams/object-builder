package com.jparams.object.builder.type;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.jparams.object.builder.utils.CollectionUtils;

/**
 * A Type is an internal representation of a Java type, optionally with a list of generic types defined.
 */
public class Type<T>
{
    private final Class<T> javaType;
    private final List<Type<?>> generics;

    Type(final Class<T> javaType, final List<Type<?>> generics)
    {
        this.javaType = javaType;
        this.generics = CollectionUtils.unmodifiableCopy(generics);
    }

    public Class<T> getJavaType()
    {
        return javaType;
    }

    public List<Type<?>> getGenerics()
    {
        return generics;
    }

    /**
     * This method allows you to create a type with generics. For example, we can represent a Map&lt;String, String&gt; as:
     *
     * <pre>
     *     Type.forClass(Map.class).withGenerics(Arrays.asList(Type.forClass(String.class), Type.forClass(String.class)))
     * </pre>
     *
     * If you want to represent a type with generics within generics, such as Map&lt;String, List&lt;String&gt;&gt; as, we can do this as follows:
     *
     * <pre>
     *     Type.forClass(Map.class).withGenerics(Arrays.asList(Type.forClass(String.class), Type.forClass(List.class).withGenerics(String.class)))
     * </pre>
     *
     * @param generics generics
     * @return type with generics
     * @throws IllegalArgumentException thrown when to many or too few generics provided
     */
    public Type<T> withGenerics(final List<Type<?>> generics) throws IllegalArgumentException
    {
        validateGenerics(generics);
        return new Type<>(javaType, generics);
    }

    /**
     * This method allows you to create a type with generics. For example, we can represent a List&lt;String&gt; as:
     *
     * <pre>
     *     Type.forClass(List.class).withGenerics(Type.forClass(String.class))
     * </pre>
     *
     * If you want to represent a type with generics within generics, such as List&lt;List&lt;String&gt;&gt;, you can do this as follows:
     *
     * <pre>
     *     Type.forClass(List.class).withGenerics(Type.forClass(List.class).withGenerics(String.class))
     * </pre>
     *
     * @param generics generics
     * @return type with generics
     * @throws IllegalArgumentException thrown when to many or too few generics provided
     */
    public Type<T> withGenerics(final Type<?>... generics) throws IllegalArgumentException
    {
        return withGenerics(Arrays.asList(generics));
    }

    /**
     * This method allows you to create a type with generics. For example, we can represent a List&lt;String&gt; as:
     *
     * <pre>
     *     Type.forClass(List.class).withGenerics(String.class)
     * </pre>
     *
     * If you want to represent a type with generics within generics, see {@link Type#withGenerics(Type[])}.
     *
     * @param generics generics
     * @return type with generics
     * @throws IllegalArgumentException thrown when to many or too few generics provided
     */
    public Type<T> withGenerics(final Class<?>... generics) throws IllegalArgumentException
    {
        return withGenerics(Arrays.stream(generics).map(Type::forClass).collect(Collectors.toList()));
    }

    private void validateGenerics(final List<Type<?>> generics)
    {
        final int expectedSize = javaType.getTypeParameters().length;

        if (generics.size() != expectedSize)
        {
            throw new IllegalArgumentException("Expected " + expectedSize + " generics, got " + generics.size());
        }
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

        final Type<?> that = (Type<?>) other;
        return Objects.equals(javaType, that.javaType) && Objects.equals(generics, that.generics);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(javaType, generics);
    }

    /**
     * Build a new type for a given class
     *
     * @param clazz class to wrap
     * @param <T>   type
     * @return builder
     */
    public static <T> Type<T> forClass(final Class<T> clazz)
    {
        return new Type<>(clazz, Collections.emptyList());
    }
}
