package com.jparams.object.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import com.jparams.object.builder.path.PathFilter;
import com.jparams.object.builder.provider.AnyValueTypeProvider;
import com.jparams.object.builder.provider.ArrayProvider;
import com.jparams.object.builder.provider.BigDecimalProvider;
import com.jparams.object.builder.provider.BooleanProvider;
import com.jparams.object.builder.provider.ByteProvider;
import com.jparams.object.builder.provider.CachingProvider;
import com.jparams.object.builder.provider.CharProvider;
import com.jparams.object.builder.provider.ConcurrentMapProvider;
import com.jparams.object.builder.provider.DateProvider;
import com.jparams.object.builder.provider.DequeProvider;
import com.jparams.object.builder.provider.DoubleProvider;
import com.jparams.object.builder.provider.EnumProvider;
import com.jparams.object.builder.provider.FloatProvider;
import com.jparams.object.builder.provider.IntegerProvider;
import com.jparams.object.builder.provider.InterfaceProvider;
import com.jparams.object.builder.provider.ListProvider;
import com.jparams.object.builder.provider.LocalDateProvider;
import com.jparams.object.builder.provider.LocalDateTimeProvider;
import com.jparams.object.builder.provider.LocalTimeProvider;
import com.jparams.object.builder.provider.LongProvider;
import com.jparams.object.builder.provider.MapProvider;
import com.jparams.object.builder.provider.NullProvider;
import com.jparams.object.builder.provider.ObjectProvider;
import com.jparams.object.builder.provider.OffsetDateTimeProvider;
import com.jparams.object.builder.provider.PrefabValueProvider;
import com.jparams.object.builder.provider.Provider;
import com.jparams.object.builder.provider.QueueProvider;
import com.jparams.object.builder.provider.SetProvider;
import com.jparams.object.builder.provider.SortedMapProvider;
import com.jparams.object.builder.provider.SortedSetProvider;
import com.jparams.object.builder.provider.StringProvider;
import com.jparams.object.builder.provider.VectorProvider;
import com.jparams.object.builder.provider.ZonedDateTimeProvider;
import com.jparams.object.builder.type.MemberType;

public final class Configuration
{
    private final List<Provider> providers;
    private final Map<Class<?>, Object> prefabValueMap;
    private final Map<Class<?>, BuildStrategy> buildStrategyMap;
    private BuildStrategy defaultBuildStrategy;
    private PathFilter pathFilter;
    private Provider nullProvider;
    private int maxDepth;
    private Predicate<MemberType> cachePredicate;
    private boolean failOnError;
    private boolean failOnWarning;

    private Configuration()
    {
        this.providers = new ArrayList<>();
        this.prefabValueMap = new HashMap<>();
        this.buildStrategyMap = new HashMap<>();
        this.defaultBuildStrategy = BuildStrategy.AUTO;
        this.pathFilter = (path) -> true;
        this.nullProvider = new NullProvider();
        this.maxDepth = 15;
        this.cachePredicate = memberType -> false;
        this.failOnError = true;
        this.failOnWarning = false;
    }

    public Configuration withMaxDepth(final int maxDepth)
    {
        this.maxDepth = maxDepth;
        return this;
    }

    public Configuration withPathFilter(final PathFilter pathFilter)
    {
        this.pathFilter = pathFilter;
        return this;
    }

    public <T extends AnyValueTypeProvider> Configuration withNullProvider(final T nullProvider)
    {
        this.nullProvider = nullProvider;
        return this;
    }

    public Configuration withFailOnError(final boolean failOnError)
    {
        this.failOnError = failOnError;
        return this;
    }

    public Configuration withFailOnWarning(final boolean failOnWarning)
    {
        this.failOnWarning = failOnWarning;
        return this;
    }

    public Configuration withProvider(final Provider provider)
    {
        providers.add(provider);
        return this;
    }

    public Configuration withCacheAll()
    {
        this.cachePredicate = (memberType) -> true;
        return this;
    }

    public Configuration withCachingOnly(final MemberType... memberTypes)
    {
        return withCachingOnly(Arrays.asList(memberTypes));
    }

    public Configuration withCachingOnly(final Collection<MemberType> memberTypes)
    {
        this.cachePredicate = memberTypes::contains;
        return this;
    }

    public Configuration withCachingAllExcluding(final MemberType... memberTypes)
    {
        return withCachingAllExcluding(Arrays.asList(memberTypes));
    }

    public Configuration withCachingAllExcluding(final Collection<MemberType> memberTypes)
    {
        this.cachePredicate = memberType -> !memberTypes.contains(memberType);
        return this;
    }

    public Configuration withCaching(final Predicate<MemberType> predicate)
    {
        this.cachePredicate = predicate;
        return this;
    }

    public <T> Configuration withPrefabValue(final Class<T> clazz, final T value)
    {
        this.prefabValueMap.put(clazz, value);
        return this;
    }

    public Configuration withDefaultBuildStrategy(final BuildStrategy defaultBuildStrategy)
    {
        this.defaultBuildStrategy = defaultBuildStrategy;
        return this;
    }

    public Configuration withBuildStrategy(final Class<?> clazz, final BuildStrategy buildStrategy)
    {
        this.buildStrategyMap.put(clazz, buildStrategy);
        return this;
    }

    ObjectFactory buildObjectFactory()
    {
        return new ObjectFactory(buildProviders(), nullProvider, pathFilter, maxDepth, failOnError, failOnWarning);
    }

    private List<Provider> buildProviders()
    {
        // combine user defined providers with default providers
        final List<Provider> providers = new ArrayList<>(this.providers);
        providers.addAll(getDefaultProviders());
        providers.add(0, new PrefabValueProvider(prefabValueMap));
        providers.add(new ObjectProvider(defaultBuildStrategy, buildStrategyMap));
        providers.add(nullProvider);

        return Collections.singletonList(new CachingProvider(providers, cachePredicate));
    }

    private static List<Provider> getDefaultProviders()
    {
        final List<Provider> providers = new ArrayList<>();
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
        providers.add(new ZonedDateTimeProvider());
        providers.add(new OffsetDateTimeProvider());
        providers.add(new LongProvider());
        providers.add(new MapProvider());
        providers.add(new SortedMapProvider());
        providers.add(new ConcurrentMapProvider());
        providers.add(new SetProvider());
        providers.add(new QueueProvider());
        providers.add(new DequeProvider());
        providers.add(new VectorProvider());
        providers.add(new SortedSetProvider());
        providers.add(new StringProvider());
        providers.add(new ByteProvider());
        providers.add(new CharProvider());
        providers.add(new InterfaceProvider());
        return providers;
    }

    public static Configuration defaultConfiguration()
    {
        return new Configuration();
    }
}
