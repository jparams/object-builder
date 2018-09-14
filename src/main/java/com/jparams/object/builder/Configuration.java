package com.jparams.object.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
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
import com.jparams.object.builder.type.Type;
import com.jparams.object.builder.type.TypeMap;
import com.jparams.object.builder.type.TypeSet;

import static com.jparams.object.builder.util.Assertion.ifNotNull;

/**
 * Provides configuration used by {@link ObjectBuilder}. Create an instance of Configuration with default settings by calling
 * {@link Configuration#defaultConfiguration()} and apply overrides.
 */
public final class Configuration
{
    private final List<Provider> providers;
    private final TypeMap<Object> prefabValueMap;
    private final TypeMap<BuildStrategy> buildStrategyMap;
    private BuildStrategy defaultBuildStrategy;
    private PathFilter pathFilter;
    private Provider nullProvider;
    private int maxDepth;
    private Predicate<Type> cachePredicate;
    private boolean failOnError;
    private boolean failOnWarning;

    private Configuration()
    {
        this.providers = new ArrayList<>();
        this.prefabValueMap = new TypeMap<>();
        this.buildStrategyMap = new TypeMap<>();
        this.defaultBuildStrategy = BuildStrategy.AUTO;
        this.pathFilter = (path) -> true;
        this.nullProvider = new NullProvider();
        this.maxDepth = 15;
        this.cachePredicate = memberType -> false;
        this.failOnError = false;
        this.failOnWarning = false;
    }

    /**
     * Define the max depth to which ObjectBuilder should build to. Once the max depth is breached, all subsequent values will be built
     * as null. This is to avoid infinite recursion. This defaults to 15 if not set.
     *
     * @param maxDepth max depth
     * @return this
     */
    public Configuration withMaxDepth(final int maxDepth)
    {
        this.maxDepth = maxDepth;
        return this;
    }

    /**
     * Filter out paths that should not be built. All paths filtered will be built as null.
     *
     * @param pathFilter path filter
     * @return this
     */
    public Configuration withPathFilter(final PathFilter pathFilter)
    {
        this.pathFilter = ifNotNull(pathFilter);
        return this;
    }

    /**
     * Configure the provider that will be used to generate null values. This defaults to {@link NullProvider}, which returns null for all class types
     * and null equivalent values for primitives, such as 0 for int.
     *
     * @param nullProvider null provided
     * @param <T>          provider type
     * @return this
     */
    public <T extends AnyValueTypeProvider> Configuration withNullProvider(final T nullProvider)
    {
        this.nullProvider = ifNotNull(nullProvider);
        return this;
    }

    /**
     * If set to true, the builder will throw an exception on the first occurrence of an error level issue. If set to false, the issue will be logged
     * and a recovery strategy applied. This is set to false by default.
     *
     * @param failOnError fail on error
     * @return this
     */
    public Configuration withFailOnError(final boolean failOnError)
    {
        this.failOnError = failOnError;
        return this;
    }

    /**
     * If set to true, the builder will throw an exception on the first occurrence of an warning level issue. If set to false, the issue will be
     * logged and a recovery strategy applied. This is set to false by default.
     *
     * @param failOnWarning fail on warning
     * @return this
     */
    public Configuration withFailOnWarning(final boolean failOnWarning)
    {
        this.failOnWarning = failOnWarning;
        return this;
    }

    /**
     * Add a custom provider. Providers are prioritised in the order added. User added providers take precedence over the default providers.
     *
     * @param provider provider
     * @return this
     */
    public Configuration withProvider(final Provider provider)
    {
        providers.add(ifNotNull(provider));
        return this;
    }

    /**
     * Enable caching of all objects. Once an object of a certain type is built, it will be cached and returned each time that type is
     * requested. The cache exists for each instance of {@link ObjectBuilder} and not shared across multiple instances. Note, calling one of the
     * other caching methods such as {@link #withCachingAllExcluding(Type...)}, {@link #withCachingOnly(Type...)}, or
     * {@link #withCaching(Predicate)}, will unset any settings defined by this method call.
     *
     * @return this
     */
    public Configuration withCacheAll()
    {
        this.cachePredicate = (type) -> true;
        return this;
    }

    /**
     * Enable caching only on the given types. Once an object of a certain type is built, it will be cached and returned each time that type is
     * requested. The cache exists for each instance of {@link ObjectBuilder} and not shared across multiple instances. Note, calling one of the
     * other caching methods such as {@link #withCachingAllExcluding(Type...)}, {@link #withCacheAll()}, or {@link #withCaching(Predicate)}, will
     * unset any settings defined by this method call.
     *
     * @param types types to cache
     * @return this
     */
    public Configuration withCachingOnly(final Type... types)
    {
        return withCachingOnly(Arrays.asList(types));
    }

    /**
     * Enable caching only on the given types. Once an object of a certain type is built, it will be cached and returned each time that type is
     * requested. The cache exists for each instance of {@link ObjectBuilder} and not shared across multiple instances. Note, calling one of the
     * other caching methods such as {@link #withCachingAllExcluding(Type...)}, {@link #withCacheAll()}, or {@link #withCaching(Predicate)}, will
     * unset any settings defined by this method call.
     *
     * @param types types to cache
     * @return this
     */
    public Configuration withCachingOnly(final Collection<Type> types)
    {
        final TypeSet set = new TypeSet(types);
        return withCaching(set::contains);
    }

    /**
     * Enable caching on all types excluding these. Once an object of this type is built, it will not be cached, instead the {@link Provider} will
     * be called each time an object of this type is required. Note, calling one of the other caching methods such as
     * {@link #withCachingOnly(Type...)}, {@link #withCacheAll()}, or {@link #withCaching(Predicate)}, will unset any settings defined by
     * this method call.
     *
     * @param types types not to cache
     * @return this
     */
    public Configuration withCachingAllExcluding(final Type... types)
    {
        return withCachingAllExcluding(Arrays.asList(types));
    }

    /**
     * Enable caching on all types excluding these. Once an object of this type is built, it will not be cached, instead the {@link Provider} will
     * be called each time an object of this type is required. Note, calling one of the other caching methods such as
     * {@link #withCachingOnly(Type...)}, {@link #withCacheAll()}, or {@link #withCaching(Predicate)}, will unset any settings defined by
     * this method call.
     *
     * @param types types not to cache
     * @return this
     */
    public Configuration withCachingAllExcluding(final Collection<Type> types)
    {
        final TypeSet set = new TypeSet(types);
        return withCaching(type -> !set.contains(type));
    }

    /**
     * Enable caching only on all types matching this predicate. Once an object of a matching type is built, it will be cached and returned each time
     * that type is requested. The cache exists for each instance of {@link ObjectBuilder} and not shared across multiple instances. Note, calling
     * one of the other caching methods such as {@link #withCachingAllExcluding(Type...)}, {@link #withCacheAll()}, or
     * {@link #withCachingOnly(Collection)}, will unset any settings defined by this method call.
     *
     * @param predicate predicate to decide if a type should be cached
     * @return this
     */
    public Configuration withCaching(final Predicate<Type> predicate)
    {
        this.cachePredicate = predicate;
        return this;
    }

    /**
     * Define a prefabricated for a given type. If a value of this type is required, the prefabricated value will be returned, avoid a call to a
     * suitable {@link Provider}.
     *
     * @param type  type
     * @param value value
     * @return this
     */
    public Configuration withPrefabValue(final Type type, final Object value)
    {
        this.prefabValueMap.put(ifNotNull(type), value);
        return this;
    }

    /**
     * The default build strategy described how objects should be instantiated. These are objects that have not been prefabricated and are not
     * supported by a specific {@link Provider}. The default build strategy will be used for all types unless a specific build strategy has been
     * configured at type level by calling {@link Configuration#withBuildStrategy(Type, BuildStrategy)}. If unspecified, the defaultBuildStrategy of
     * {@link BuildStrategy#AUTO} will be used.
     *
     * @param defaultBuildStrategy default build strategy
     * @return this
     */
    public Configuration withDefaultBuildStrategy(final BuildStrategy defaultBuildStrategy)
    {
        this.defaultBuildStrategy = ifNotNull(defaultBuildStrategy);
        return this;
    }

    /**
     * This will override the default build strategy set for a specific type.
     *
     * @param type          type
     * @param buildStrategy build strategy
     * @return this
     */
    public Configuration withBuildStrategy(final Type type, final BuildStrategy buildStrategy)
    {
        this.buildStrategyMap.put(ifNotNull(type), ifNotNull(buildStrategy));
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

    /**
     * Create a new instance of {@link Configuration} by default settings pre-configured. You can then override the defaults as required.
     *
     * @return configuration
     */
    public static Configuration defaultConfiguration()
    {
        return new Configuration();
    }
}
