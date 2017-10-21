package com.jparams.object.builder;

import com.jparams.object.builder.factory.ObjectFactory;
import com.jparams.object.builder.path.Path;
import com.jparams.object.builder.type.MemberType;
import com.jparams.object.builder.type.MemberTypeResolver;
import com.jparams.object.builder.type.TypeReference;

public class ObjectBuilder
{
    private final ObjectFactory objectFactory;

    private ObjectBuilder(final ObjectFactory objectFactory)
    {
        this.objectFactory = objectFactory;
    }

    public <T> T buildInstanceOf(final Class<T> clazz)
    {
        final MemberType memberType = MemberTypeResolver.resolve(clazz);
        final Path path = new Path("$", memberType, null);
        return objectFactory.create(path);
    }

    public <T> T buildInstanceOf(final TypeReference<T> typeReference)
    {
        if (typeReference == null || typeReference.getPath() == null)
        {
            return null;
        }

        return objectFactory.create(typeReference.getPath());
    }

    public static ObjectBuilder withDefaultConfiguration()
    {
        final Configuration configuration = new Configuration().withDefaultProviders();
        return withConfiguration(configuration);
    }

    public static ObjectBuilder withConfiguration(final Configuration configuration)
    {
        final ObjectFactory objectFactory = configuration.createObjectFactory();
        return new ObjectBuilder(objectFactory);
    }
}
