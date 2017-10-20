package com.jparams.object.builder.type;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collections;

import com.jparams.object.builder.path.Path;
import com.jparams.object.builder.util.ReflectionUtils;

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
            final ParameterizedType superClass = (ParameterizedType) typeReference.getClass().getGenericSuperclass();

            if (superClass.getActualTypeArguments().length > 0)
            {
                if (superClass.getActualTypeArguments()[0] instanceof ParameterizedType)
                {
                    final ParameterizedType parameterizedType = (ParameterizedType) superClass.getActualTypeArguments()[0];
                    final Class<?>[] generics = ReflectionUtils.getGenerics(parameterizedType);

                    return new Path("$", (Class<?>) parameterizedType.getRawType(), Arrays.asList(generics), null);
                }

                if (superClass.getActualTypeArguments()[0] instanceof Class)
                {
                    return new Path("$", (Class<?>) superClass.getActualTypeArguments()[0], Collections.emptyList(), null);
                }
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
