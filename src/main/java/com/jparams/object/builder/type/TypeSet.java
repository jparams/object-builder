package com.jparams.object.builder.type;

import java.util.Collection;

public class TypeSet
{
    private final TypeMap<String> typeMap = new TypeMap<>();

    public TypeSet(final Collection<Type<?>> types)
    {
        types.forEach(type -> typeMap.put(type, ""));
    }

    public boolean contains(final Type<?> type)
    {
        return typeMap.findMatch(type).isPresent();
    }
}
