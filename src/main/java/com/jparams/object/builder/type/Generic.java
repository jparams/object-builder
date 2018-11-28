package com.jparams.object.builder.type;

import java.util.Objects;

/**
 * Generic argument on a type deceleration
 */
public class Generic
{
    private final String alias;
    private final Type<?> type;

    public Generic(final String alias, final Type<?> type)
    {
        this.alias = alias;
        this.type = type;
    }

    public String getAlias()
    {
        return alias;
    }

    public Type<?> getType()
    {
        return type;
    }

    @Override
    public boolean equals(final Object other)
    {
        if (this == other)
        {
            return true;
        }

        if (other == null || getClass() != other.getClass())
        {
            return false;
        }

        final Generic generic = (Generic) other;
        return Objects.equals(alias, generic.alias) && Objects.equals(type, generic.type);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(alias, type);
    }

    @Override
    public String toString()
    {
        return alias + ":" + type;
    }
}
