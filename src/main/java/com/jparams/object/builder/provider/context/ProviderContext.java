package com.jparams.object.builder.provider.context;

import com.jparams.object.builder.factory.ObjectFactory;
import com.jparams.object.builder.path.Path;

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

    public ObjectFactory getObjectFactory()
    {
        return objectFactory;
    }
}
