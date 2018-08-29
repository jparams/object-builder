package com.jparams.object.builder.issue;

import com.jparams.object.builder.path.Path;
import com.jparams.object.builder.type.MemberType;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IssueTest
{
    @Test
    public void testToString()
    {
        final Issue issue = new Issue(IssueType.WARNING, "some message", new Path("$", new MemberType(String.class), null), null);
        assertThat(issue).hasToString("WARNING at path: $\n"
                                          + "Message: some message");
    }

    @Test
    public void testToStringWithCause()
    {
        final Issue issue = new Issue(IssueType.ERROR, "some message", new Path("$", new MemberType(String.class), null), new Exception("abc"));
        assertThat(issue.toString()).startsWith("ERROR at path: $\n"
                                                    + "Message: some message\n"
                                                    + "Cause:\n"
                                                    + "java.lang.Exception: abc");
    }
}