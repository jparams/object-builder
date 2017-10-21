package com.jparams.object.builder.provider.context;

import com.jparams.object.builder.factory.ObjectFactory;
import com.jparams.object.builder.path.Path;
import com.jparams.object.builder.type.MemberType;

public class ProviderContext
{
    private final Path path;
    private final ObjectFactory objectFactory;

    public ProviderContext(final Path path, final ObjectFactory objectFactory)
    {
        this.path = path;
        this.objectFactory = objectFactory;
    }

    public Path getPath()
    {
        return path;
    }

    public Object createChild(final String name, final MemberType memberType)
    {
        final Path childPath = new Path(name, memberType, this.path);
        return objectFactory.create(childPath);
    }
}
