package com.jparams.object.builder.issue;

import com.jparams.object.builder.path.Path;

public class Issue
{
    private final IssueType issueType;
    private final String message;
    private final Path path;
    private final Exception cause;

    public Issue(final IssueType issueType, final String message, final Path path, final Exception cause)
    {
        this.issueType = issueType;
        this.message = message;
        this.path = path;
        this.cause = cause;
    }

    public IssueType getIssueType()
    {
        return issueType;
    }

    public String getMessage()
    {
        return message;
    }

    public Path getPath()
    {
        return path;
    }

    public Exception getCause()
    {
        return cause;
    }
}
