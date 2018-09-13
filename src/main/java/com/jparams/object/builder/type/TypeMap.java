package com.jparams.object.builder.type;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

public class TypeMap<V>
{
    private final Map<Type, V> map = new LinkedHashMap<>();

    public TypeMap()
    {
    }

    public void put(final Type type, final V value)
    {
        map.put(type, value);
    }

    public Optional<V> findMatch(final Type type)
    {
        if (map.containsKey(type))
        {
            return Optional.of(map.get(type));
        }

        // on exact match fail, try best match
        return map.entrySet()
                  .stream()
                  .filter(entry -> entry.getKey().getGenerics().isEmpty() && type.getJavaType().equals(entry.getKey().getJavaType()))
                  .map(Entry::getValue)
                  .findFirst();
    }
}
