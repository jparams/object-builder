package com.jparams.object.builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jparams.object.builder.factory.ObjectFactory;
import com.jparams.object.builder.path.Path;
import com.jparams.object.builder.path.PathFactory;
import com.jparams.object.builder.path.PathFilter;
import com.jparams.object.builder.provider.ArrayProvider;
import com.jparams.object.builder.provider.BigDecimalProvider;
import com.jparams.object.builder.provider.BooleanProvider;
import com.jparams.object.builder.provider.ByteProvider;
import com.jparams.object.builder.provider.CharProvider;
import com.jparams.object.builder.provider.DateProvider;
import com.jparams.object.builder.provider.DoubleProvider;
import com.jparams.object.builder.provider.EnumProvider;
import com.jparams.object.builder.provider.FloatProvider;
import com.jparams.object.builder.provider.IntegerProvider;
import com.jparams.object.builder.provider.InterfaceProxyProvider;
import com.jparams.object.builder.provider.ListProvider;
import com.jparams.object.builder.provider.LocalDateProvider;
import com.jparams.object.builder.provider.LocalDateTimeProvider;
import com.jparams.object.builder.provider.LocalTimeProvider;
import com.jparams.object.builder.provider.LongProvider;
import com.jparams.object.builder.provider.MapProvider;
import com.jparams.object.builder.provider.NullProvider;
import com.jparams.object.builder.provider.ObjectProvider;
import com.jparams.object.builder.provider.Provider;
import com.jparams.object.builder.provider.SetProvider;
import com.jparams.object.builder.provider.StringProvider;
import com.jparams.object.builder.provider.cache.CachedDataProvider;

public class ObjectBuilder
{
    private final List<Provider> providers;
    private PathFilter pathFilter;
    private Provider nullProvider;
    private int maxDepth;
    private boolean caching;
    private int cacheStart;

    private ObjectBuilder()
    {
        this.providers = new ArrayList<>();
        this.pathFilter = (path) -> true;
        this.nullProvider = new NullProvider();
        this.maxDepth = 10;
        this.caching = false;
        this.cacheStart = 0;
    }

    public ObjectBuilder withMaxDepth(final int maxDepth)
    {
        this.maxDepth = maxDepth;
        return this;
    }

    public ObjectBuilder withPathFilter(final PathFilter pathFilter)
    {
        this.pathFilter = pathFilter;
        return this;
    }

    public ObjectBuilder withNullProvider(final Provider nullProvider)
    {
        this.nullProvider = nullProvider;
        return this;
    }

    public ObjectBuilder withProvider(final Provider provider)
    {
        providers.add(0, provider);
        return this;
    }

    public ObjectBuilder withCaching(final boolean caching)
    {
        this.caching = caching;
        return this;
    }

    public ObjectBuilder withCacheStart(final int cacheStart)
    {
        this.cacheStart = cacheStart;
        return this;
    }

    public ObjectBuilder withDefaultProviders()
    {
        providers.add(new ArrayProvider());
        providers.add(new BigDecimalProvider());
        providers.add(new BooleanProvider());
        providers.add(new DateProvider());
        providers.add(new DoubleProvider());
        providers.add(new EnumProvider());
        providers.add(new FloatProvider());
        providers.add(new IntegerProvider());
        providers.add(new ListProvider());
        providers.add(new LocalDateProvider());
        providers.add(new LocalDateTimeProvider());
        providers.add(new LocalTimeProvider());
        providers.add(new LongProvider());
        providers.add(new MapProvider());
        providers.add(new SetProvider());
        providers.add(new StringProvider());
        providers.add(new ByteProvider());
        providers.add(new CharProvider());
        providers.add(new InterfaceProxyProvider());
        providers.add(new ObjectProvider());
        providers.add(new NullProvider());
        return this;
    }

    public <T> T buildInstanceOf(final Class<T> clazz)
    {
        final ObjectFactory objectFactory = new ObjectFactory(getProviders(), nullProvider, pathFilter, maxDepth);
        final Path rootPath = PathFactory.createRootPath(clazz);
        return objectFactory.create(rootPath);
    }

    public static ObjectBuilder newBuilder()
    {
        return new ObjectBuilder();
    }

    private List<Provider> getProviders()
    {
        if (caching)
        {
            return Collections.singletonList(new CachedDataProvider(providers, cacheStart));
        }

        return providers;
    }
}
