package com.jparams.object.builder.path;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Path
{
    private final String name;
    private final Class<?> type;
    private final Path parent;
    private final List<Class<?>> generics;

    public Path(final String name, final Class<?> type, final List<Class<?>> generics, final Path parent)
    {
        this.name = name;
        this.type = type;
        this.parent = parent;
        this.generics = Collections.unmodifiableList(generics);
    }

    public String getName()
    {
        return name;
    }

    public Class<?> getType()
    {
        return type;
    }

    public Path getParent()
    {
        return parent;
    }

    public List<Class<?>> getGenerics()
    {
        return generics;
    }

    public int getDepth()
    {
        return parent == null ? 0 : parent.getDepth() + 1;
    }

    public String getLocation()
    {
        return parent == null ? name : parent.getLocation() + "." + name;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }

        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        final Path path = (Path) o;
        return Objects.equals(name, path.name)
            && Objects.equals(type, path.type)
            && Objects.equals(parent, path.parent)
            && Objects.equals(generics, path.generics);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name, type, parent, generics);
    }
}
