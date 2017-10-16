package com.jparams.object.builder.path;

import java.util.Collections;
import java.util.List;

public class Path
{
    private final String name;
    private final Class<?> type;
    private final Path parent;
    private final List<Class<?>> generics;

    Path(final String name, final Class<?> type, final List<Class<?>> generics, final Path parent)
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
}
