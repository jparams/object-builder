package com.jparams.object.builder;

import com.jparams.object.builder.path.Path;
import com.jparams.object.builder.type.Type;
import com.jparams.object.builder.type.TypeReference;
import com.jparams.object.builder.type.TypeResolver;

public class ObjectBuilder
{
    private final ObjectFactory objectFactory;

    private ObjectBuilder(final ObjectFactory objectFactory)
    {
        this.objectFactory = objectFactory;
    }

    public <T> Build<T> buildInstanceOf(final Class<T> clazz)
    {
        final Type memberType = TypeResolver.resolve(clazz);
        final Path path = new Path("$", memberType, null);
        return objectFactory.create(path);
    }

    public <T> Build<T> buildInstanceOf(final TypeReference<T> typeReference)
    {
        if (typeReference == null || typeReference.getPath() == null)
        {
            return null;
        }

        return objectFactory.create(typeReference.getPath());
    }

    public static ObjectBuilder withDefaultConfiguration()
    {
        final ObjectFactory objectFactory = Configuration.defaultConfiguration().buildObjectFactory();
        return new ObjectBuilder(objectFactory);
    }

    public static ObjectBuilder withConfiguration(final Configuration configuration)
    {
        final ObjectFactory objectFactory = configuration.buildObjectFactory();
        return new ObjectBuilder(objectFactory);
    }
}
