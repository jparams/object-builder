package com.jparams.object.builder.model;

public class MyModel4<T>
{
    private final T obj;

    public MyModel4(final T obj)
    {
        this.obj = obj;
    }

    public T getObj()
    {
        return obj;
    }
}
