package com.jparams.object.builder.model;

public class MyModel5<T>
{
    private final MyModel4<T> obj;

    public MyModel5(final MyModel4<T> obj)
    {
        this.obj = obj;
    }

    public MyModel4<T> getObj()
    {
        return obj;
    }
}
