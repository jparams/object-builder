package com.jparams.object.builder.type;

import java.lang.reflect.ParameterizedType;

import com.jparams.object.builder.path.Path;

public abstract class TypeReference<T> implements Comparable<T>
{
    private final Path path;

    protected TypeReference()
    {
        this.path = createPath(this);
    }

    public Path getPath()
    {
        return path;
    }

    private static Path createPath(final TypeReference<?> typeReference)
    {
        if (typeReference.getClass().getGenericSuperclass() instanceof ParameterizedType)
        {
            final java.lang.reflect.Type[] types = ((ParameterizedType) typeReference.getClass().getGenericSuperclass()).getActualTypeArguments();

            if (types.length > 0)
            {
                final Type<?> type = TypeResolver.resolve(types[0]);
                return new Path("$", type, null);
            }
        }

        return null;
    }

    @Override
    public int compareTo(final T obj)
    {
        return 0;
    }
}
