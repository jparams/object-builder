package com.jparams.object.builder.type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MemberType
{
    private final Class<?> type;
    private final List<MemberType> generics;

    public MemberType(final Class<?> type)
    {
        this.type = type;
        this.generics = Collections.emptyList();
    }

    public MemberType(final Class<?> type, final List<MemberType> generics)
    {
        this.type = type;
        this.generics = generics == null ? Collections.emptyList() : Collections.unmodifiableList(new ArrayList<>(generics));
    }

    public Class<?> getType()
    {
        return type;
    }

    public List<MemberType> getGenerics()
    {
        return generics;
    }

    @Override
    public boolean equals(final Object other)
    {
        if (this == other)
        {
            return true;
        }

        if (!(other instanceof MemberType))
        {
            return false;
        }

        final MemberType that = (MemberType) other;
        return Objects.equals(type, that.type) &&
            Objects.equals(generics, that.generics);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(type, generics);
    }
}
