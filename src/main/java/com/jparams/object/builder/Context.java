package com.jparams.object.builder;

import com.jparams.object.builder.issue.Issue;
import com.jparams.object.builder.issue.IssueLogger;
import com.jparams.object.builder.issue.IssueType;
import com.jparams.object.builder.path.Path;
import com.jparams.object.builder.type.MemberType;

public class Context
{
    private final Path path;
    private final ObjectFactory objectFactory;
    private final IssueLogger issueLogger;

    public Context(final Path path, final ObjectFactory objectFactory, final IssueLogger issueLogger)
    {
        this.path = path;
        this.objectFactory = objectFactory;
        this.issueLogger = issueLogger;
    }

    public Path getPath()
    {
        return path;
    }

    public Object createChild(final String name, final MemberType memberType)
    {
        final Path childPath = new Path(name, memberType, this.path);
        final Build<Object> build = objectFactory.create(childPath, issueLogger);
        return build.get();
    }

    public void logWarning(final String message)
    {
        issueLogger.log(new Issue(IssueType.WARNING, message, path, null));
    }

    public void logError(final String message)
    {
        issueLogger.log(new Issue(IssueType.ERROR, message, path, null));
    }

    public void logError(final String message, final Exception exception)
    {
        issueLogger.log(new Issue(IssueType.ERROR, message, path, exception));
    }
}
