package com.jparams.object.builder;

import java.util.List;
import java.util.stream.Collectors;

import com.jparams.object.builder.issue.Issue;
import com.jparams.object.builder.issue.IssueType;
import com.jparams.object.builder.utils.CollectionUtils;

public class Build<T>
{
    private final T obj;
    private final List<Issue> issues;

    public Build(final T obj, final List<Issue> issues)
    {
        this.obj = obj;
        this.issues = CollectionUtils.unmodifiableCopy(issues);
    }

    public T get()
    {
        return obj;
    }

    public List<Issue> getIssues()
    {
        return issues;
    }

    public List<Issue> getErrors()
    {
        return issues.stream()
                     .filter(issue -> issue.getIssueType() == IssueType.ERROR)
                     .collect(Collectors.toList());
    }

    public List<Issue> getWarnings()
    {
        return issues.stream()
                     .filter(issue -> issue.getIssueType() == IssueType.WARNING)
                     .collect(Collectors.toList());
    }
}
