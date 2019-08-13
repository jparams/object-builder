package com.jparams.object.builder.utils;

import java.util.ArrayList;
import java.util.List;

import com.jparams.object.builder.util.CollectionUtils;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CollectionUtilsTest
{
    @Test
    public void testUnmodifiableCopyOfNull()
    {
        final List<Object> copy = CollectionUtils.unmodifiableCopy(null);
        assertThat(copy).isEmpty();
    }

    @Test
    public void testUnmodifiableCopy()
    {
        final List<Object> list = new ArrayList<>();
        list.add("abc");

        final List<Object> copy = CollectionUtils.unmodifiableCopy(list);
        assertThat(copy).isEqualTo(list).isNotSameAs(list);

        assertThatThrownBy(() -> copy.add("def")).isInstanceOf(UnsupportedOperationException.class);
    }
}