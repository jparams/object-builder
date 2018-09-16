package com.jparams.object.builder.type;

import java.lang.reflect.ParameterizedType;

/**
 * This generic abstract class is used for obtaining full generics type information by sub-classing. Here is one way to instantiate reference
 * to generic type <code>List&lt;Integer&gt;</code>:
 *
 * <pre>
 *  TypeReference ref = new TypeReference&lt;List&lt;Integer&gt;&gt;() { };
 * </pre>
 */
public abstract class TypeReference<T>
{
    private final Type<T> type;

    protected TypeReference()
    {
        this.type = createType(this);
    }

    public Type<T> getType()
    {
        return type;
    }

    private static <T> Type<T> createType(final TypeReference<T> typeReference)
    {
        if (typeReference.getClass().getGenericSuperclass() instanceof ParameterizedType)
        {
            final java.lang.reflect.Type[] types = ((ParameterizedType) typeReference.getClass().getGenericSuperclass()).getActualTypeArguments();

            if (types.length > 0)
            {
                @SuppressWarnings("unchecked") final Type<T> resolvedType = (Type<T>) TypeResolver.resolve(types[0]);
                return resolvedType;
            }
        }

        return null;
    }
}
