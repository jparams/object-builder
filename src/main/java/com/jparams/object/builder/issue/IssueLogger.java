package com.jparams.object.builder.issue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jparams.object.builder.exception.BuilderFailedException;

public class IssueLogger
{
    private final List<Issue> issues;
    private final boolean failOnError;
    private final boolean failOnWarning;

    public IssueLogger(final boolean failOnError, final boolean failOnWarning)
    {
        this.failOnError = failOnError;
        this.failOnWarning = failOnWarning;
        this.issues = new ArrayList<>();
    }

    public void log(final Issue issue)
    {
        issues.add(issue);

        if (issue.getIssueType() == IssueType.WARNING && failOnWarning)
        {
            throw new BuilderFailedException("ObjectBuilder failed with the WARNING [" + issue.getMessage() + "] at path [" + issue.getPath() + "]");
        }

        if (issue.getIssueType() == IssueType.ERROR && failOnError)
        {
            throw new BuilderFailedException("ObjectBuilder failed with the ERROR [" + issue.getMessage() + "] at path [" + issue.getPath() + "]");
        }
    }

    public List<Issue> getIssues()
    {
        return Collections.unmodifiableList(issues);
    }
}
