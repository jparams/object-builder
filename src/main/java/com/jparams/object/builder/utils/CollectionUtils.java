package com.jparams.object.builder.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class CollectionUtils
{
    private CollectionUtils()
    {
    }

    public static <T> List<T> unmodifiableCopy(final List<T> list)
    {
        return list == null ? Collections.emptyList() : Collections.unmodifiableList(new ArrayList<>(list));
    }
}
