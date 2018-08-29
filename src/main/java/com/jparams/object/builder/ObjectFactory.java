package com.jparams.object.builder;

import java.util.List;

import com.jparams.object.builder.issue.IssueLogger;
import com.jparams.object.builder.path.Path;
import com.jparams.object.builder.path.PathFilter;
import com.jparams.object.builder.provider.Provider;
import com.jparams.object.builder.utils.CollectionUtils;

class ObjectFactory
{
    private final List<Provider> providers;
    private final PathFilter pathFilter;
    private final Provider nullProvider;
    private final int maxDepth;
    private final boolean failOnError;
    private final boolean failOnWarning;

    ObjectFactory(final List<Provider> providers,
                  final Provider nullProvider,
                  final PathFilter pathFilter,
                  final int maxDepth,
                  final boolean failOnError,
                  final boolean failOnWarning)
    {
        this.providers = CollectionUtils.unmodifiableCopy(providers);
        this.nullProvider = nullProvider;
        this.pathFilter = pathFilter;
        this.maxDepth = maxDepth;
        this.failOnError = failOnError;
        this.failOnWarning = failOnWarning;
    }

    <T> Build<T> create(final Path path)
    {
        return create(path, new IssueLogger(failOnError, failOnWarning));
    }

    @SuppressWarnings("unchecked")
    <T> Build<T> create(final Path path, final IssueLogger issueLogger)
    {
        final Context context = new Context(path, this, issueLogger);

        if (path.getDepth() > maxDepth)
        {
            context.logWarning("Max depth exhausted");
            return new Build<>((T) nullProvider.provide(context), issueLogger.getIssues());
        }

        if (!pathFilter.accept(path))
        {
            return new Build<>((T) nullProvider.provide(context), issueLogger.getIssues());
        }

        try
        {
            final T obj = (T) providers.stream()
                                       .filter(provider -> provider.supports(path.getMemberType() == null ? null : path.getMemberType().getType()))
                                       .map(provider -> provider.provide(context))
                                       .findFirst()
                                       .orElse(nullProvider.provide(context));

            return new Build<>(obj, issueLogger.getIssues());
        }
        catch (final Exception e)
        {
            return new Build<>((T) nullProvider.provide(context), issueLogger.getIssues());
        }
    }
}
