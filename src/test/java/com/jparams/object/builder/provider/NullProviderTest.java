package com.jparams.object.builder.provider;

import com.jparams.object.builder.path.Path;
import com.jparams.object.builder.provider.context.ProviderContext;
import com.jparams.object.builder.type.MemberType;
import com.jparams.object.builder.type.MemberTypeResolver;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class NullProviderTest
{
    private NullProvider subject;

    @Before
    public void setUp()
    {
        subject = new NullProvider();
    }

    @Test
    public void supportsAllTypes()
    {
        assertThat(subject.supports(null)).isTrue();
    }

    @Test
    public void createsNullValue()
    {
        assertThat(provide(String.class)).isNull();
        assertThat(provide(byte.class)).isEqualTo((byte) 0);
        assertThat(provide(short.class)).isEqualTo((short) 0);
        assertThat(provide(int.class)).isEqualTo(0);
        assertThat(provide(long.class)).isEqualTo((long) 0);
        assertThat(provide(float.class)).isEqualTo((float) 0.0);
        assertThat(provide(double.class)).isEqualTo((double) 0.0);
        assertThat(provide(boolean.class)).isEqualTo(false);
        assertThat(provide(char.class)).isEqualTo('\u0000');
    }

    private Object provide(final Class<?> clazz)
    {
        final MemberType memberType = MemberTypeResolver.resolve(clazz);
        final Path path = new Path("", memberType, null);
        final ProviderContext context = new ProviderContext(path, null);
        return subject.provide(context);
    }
}