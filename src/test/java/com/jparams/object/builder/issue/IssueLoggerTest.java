package com.jparams.object.builder.issue;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class IssueLoggerTest
{
    @Test
    public void logsWarningIssue()
    {
        final IssueLogger logger = new IssueLogger(false, false);
        final Issue issue = new Issue(IssueType.WARNING, "some message", null, null);
        logger.log(issue);
        assertThat(logger.getIssues()).containsExactly(issue);
    }

    @Test
    public void logsErrorIssue()
    {
        final IssueLogger logger = new IssueLogger(false, false);
        final Issue issue = new Issue(IssueType.ERROR, "some message", null, null);
        logger.log(issue);
        assertThat(logger.getIssues()).containsExactly(issue);
    }

    @Test
    public void throwsExceptionOnWarningIssue()
    {
        final IssueLogger logger = new IssueLogger(true, true);
        final Issue issue = new Issue(IssueType.WARNING, "some message", null, null);
        assertThatThrownBy(() -> logger.log(issue)).hasMessage("ObjectBuilder failed with the WARNING [some message] at path [null]");
        assertThat(logger.getIssues()).containsExactly(issue);
    }

    @Test
    public void throwsExceptionOnErrorIssue()
    {
        final IssueLogger logger = new IssueLogger(true, true);
        final Issue issue = new Issue(IssueType.ERROR, "some message", null, null);
        assertThatThrownBy(() -> logger.log(issue)).hasMessage("ObjectBuilder failed with the ERROR [some message] at path [null]");
        assertThat(logger.getIssues()).containsExactly(issue);
    }
}