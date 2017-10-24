package com.jparams.object.builder.type;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

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
            final Type[] types = ((ParameterizedType) typeReference.getClass().getGenericSuperclass()).getActualTypeArguments();

            if (types.length > 0)
            {
                final MemberType memberType = MemberTypeResolver.resolve(types[0]);
                return new Path("$", memberType, null);
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
