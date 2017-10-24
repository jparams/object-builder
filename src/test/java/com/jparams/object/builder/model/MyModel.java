package com.jparams.object.builder.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class MyModel
{
    private final String field1;
    private final List<MyModel2> field2;
    private final Set<BigDecimal> field3;
    private MyModel field4;

    public MyModel(final String field1, final List<MyModel2> myModel2s, final List<MyModel2> field2, final Set<BigDecimal> field3)
    {
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
    }

    public String getField1()
    {
        return field1;
    }

    public List<MyModel2> getField2()
    {
        return field2;
    }

    public Set<BigDecimal> getField3()
    {
        return field3;
    }

    public MyModel getField4()
    {
        return field4;
    }
}
