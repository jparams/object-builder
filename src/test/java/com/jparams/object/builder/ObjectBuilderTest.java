package com.jparams.object.builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jparams.object.builder.model.MyEmptyEnum;
import com.jparams.object.builder.model.MyEnum;
import com.jparams.object.builder.model.MyInterface;
import com.jparams.object.builder.model.MyModel;
import com.jparams.object.builder.model.MyModel2;
import com.jparams.object.builder.model.MyModel3;
import com.jparams.object.builder.provider.Provider;
import com.jparams.object.builder.provider.context.ProviderContext;
import com.jparams.object.builder.type.TypeReference;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ObjectBuilderTest
{
    private ObjectBuilder subject;

    /**
     * set up
     */
    @Before
    public void setUp()
    {
        subject = ObjectBuilder.withDefaultConfiguration();
    }

    @Test
    public void createsArray()
    {
        final String[] strings = subject.buildInstanceOf(String[].class);
        assertThat(strings).isNotEmpty();
        assertThat(strings).doesNotContainNull();
    }

    @Test
    public void createsBigDecimal()
    {
        final BigDecimal value = subject.buildInstanceOf(BigDecimal.class);
        assertThat(value).isNotNull();
    }

    @Test
    public void createsBoolean()
    {
        final Boolean value = subject.buildInstanceOf(Boolean.class);
        assertThat(value).isNotNull();
    }

    @Test
    public void createsBooleanPrimitive()
    {
        final boolean value = subject.buildInstanceOf(boolean.class);
        assertThat(value).isNotNull();
    }

    @Test
    public void createsByte()
    {
        final Byte value = subject.buildInstanceOf(Byte.class);
        assertThat(value).isNotNull();
    }

    @Test
    public void createsBytePrimitive()
    {
        final byte value = subject.buildInstanceOf(byte.class);
        assertThat(value).isNotNull();
    }

    @Test
    public void createsChar()
    {
        final Character value = subject.buildInstanceOf(Character.class);
        assertThat(value).isNotNull();
    }

    @Test
    public void createsCharPrimitive()
    {
        final char value = subject.buildInstanceOf(char.class);
        assertThat(value).isNotNull();
    }

    @Test
    public void createsDate()
    {
        final Date value = subject.buildInstanceOf(Date.class);
        assertThat(value).isNotNull();
    }

    @Test
    public void createsDouble()
    {
        final Double value = subject.buildInstanceOf(Double.class);
        assertThat(value).isNotNull();
    }

    @Test
    public void createsDoublePrimitive()
    {
        final double value = subject.buildInstanceOf(double.class);
        assertThat(value).isNotNull();
    }

    @Test
    public void createsFloat()
    {
        final Float value = subject.buildInstanceOf(Float.class);
        assertThat(value).isNotNull();
    }

    @Test
    public void createsFloatPrimitive()
    {
        final float value = subject.buildInstanceOf(float.class);
        assertThat(value).isNotNull();
    }

    @Test
    public void createsInteger()
    {
        final Integer value = subject.buildInstanceOf(Integer.class);
        assertThat(value).isNotNull();
    }

    @Test
    public void createsIntegerPrimitive()
    {
        final int value = subject.buildInstanceOf(int.class);
        assertThat(value).isNotNull();
    }

    @Test
    public void createsInterfaceProxy()
    {
        final MyInterface value = subject.buildInstanceOf(MyInterface.class);

        assertThat(value).isNotNull();
        assertThat(value.toString()).isNotNull();
        assertThat(value.generateString()).isNotNull();

        value.voidMethod();
    }

    @Test
    public void createsEmptyListWhenGenericsNotKnown()
    {
        final List values = subject.buildInstanceOf(List.class);
        assertThat(values).isEmpty();
    }

    @Test
    public void createsListWithTypeReference()
    {
        final List values = subject.buildInstanceOf(new TypeReference<List<String>>()
        {
        });

        assertThat(values).isNotEmpty();
        assertThat(values).doesNotContainNull();
    }

    @Test
    public void createsListWithDeepGenerics()
    {
        final List values = subject.buildInstanceOf(new TypeReference<List<List<BigDecimal>>>()
        {
        });

        assertThat(values).isNotEmpty();
        assertThat(values).doesNotContainNull();
    }

    @Test
    public void createsNullWithUnknownTypeReference()
    {
        final TypeReference<?> typeReference = new TypeReference()
        {
        };
        final Object values = subject.buildInstanceOf(typeReference);

        assertThat(values).isNull();
    }

    @Test
    public void createsLocalDateTime()
    {
        final LocalDateTime value = subject.buildInstanceOf(LocalDateTime.class);
        assertThat(value).isNotNull();
    }

    @Test
    public void createsLocalDate()
    {
        final LocalDate value = subject.buildInstanceOf(LocalDate.class);
        assertThat(value).isNotNull();
    }

    @Test
    public void createsLocalTime()
    {
        final LocalTime value = subject.buildInstanceOf(LocalTime.class);
        assertThat(value).isNotNull();
    }

    @Test
    public void createsLong()
    {
        final Long value = subject.buildInstanceOf(Long.class);
        assertThat(value).isNotNull();
    }

    @Test
    public void createsLongPrimitive()
    {
        final long value = subject.buildInstanceOf(long.class);
        assertThat(value).isNotNull();
    }

    @Test
    public void returnsCachedData()
    {
        final Configuration configuration = new Configuration().withDefaultProviders().withCaching(true).withCacheStart(0);
        final ObjectBuilder subject = ObjectBuilder.withConfiguration(configuration);

        final String str1 = subject.buildInstanceOf(String.class);
        final String str2 = subject.buildInstanceOf(String.class);

        assertThat(str1).isNotNull();
        assertThat(str1).isEqualTo(str2);
    }

    @Test
    public void returnsShallowCopy()
    {
        final Configuration configuration = new Configuration().withDefaultProviders().withCaching(true).withCacheStart(1);
        final ObjectBuilder subject = ObjectBuilder.withConfiguration(configuration);

        final MyModel myModel1 = subject.buildInstanceOf(MyModel.class);
        final MyModel myModel2 = subject.buildInstanceOf(MyModel.class);

        assertThat(myModel1).isNotSameAs(myModel2);
        assertThat(myModel1).isEqualToComparingFieldByFieldRecursively(myModel2);
    }

    @Test
    public void createsEmptyMapWhenGenericsNotKnown()
    {
        final Map values = subject.buildInstanceOf(new TypeReference<Map>()
        {
        });
        assertThat(values).isEmpty();
    }

    @Test
    public void createsMapWithTypeReference()
    {
        final Map<String, BigDecimal> values = subject.buildInstanceOf(new TypeReference<Map<String, BigDecimal>>()
        {
        });

        assertThat(values).isNotEmpty();
    }

    @Test
    public void createsEnum()
    {
        final MyEnum value = subject.buildInstanceOf(MyEnum.class);
        assertThat(value).isNotNull();
    }

    @Test
    public void createsNullEnumWhenNoValuesAvailable()
    {
        final MyEmptyEnum value = subject.buildInstanceOf(MyEmptyEnum.class);
        assertThat(value).isNull();
    }

    @Test
    public void createsNullValueWhenDepthExhausted()
    {
        final Configuration configuration = new Configuration().withMaxDepth(-1);
        final ObjectBuilder subject = ObjectBuilder.withConfiguration(configuration);

        assertThat(subject.buildInstanceOf(String.class)).isNull();
        assertThat(subject.buildInstanceOf(byte.class)).isEqualTo((byte) 0);
        assertThat(subject.buildInstanceOf(short.class)).isEqualTo((short) 0);
        assertThat(subject.buildInstanceOf(int.class)).isEqualTo(0);
        assertThat(subject.buildInstanceOf(long.class)).isEqualTo((long) 0);
        assertThat(subject.buildInstanceOf(float.class)).isEqualTo((float) 0.0);
        assertThat(subject.buildInstanceOf(double.class)).isEqualTo((double) 0.0);
        assertThat(subject.buildInstanceOf(boolean.class)).isEqualTo(false);
        assertThat(subject.buildInstanceOf(char.class)).isEqualTo('\u0000');
    }

    @Test
    public void createsEmptySetWhenGenericsNotKnown()
    {
        final Set values = subject.buildInstanceOf(new TypeReference<Set>()
        {
        });
        assertThat(values).isEmpty();
    }

    @Test
    public void createsShort()
    {
        final Short value = subject.buildInstanceOf(Short.class);
        assertThat(value).isNotNull();
    }

    @Test
    public void createsShortPrimitive()
    {
        final short value = subject.buildInstanceOf(short.class);
        assertThat(value).isNotNull();
    }

    @Test
    public void createsNullOnException()
    {
        final MyModel3 value = subject.buildInstanceOf(MyModel3.class);
        assertThat(value).isNull();
    }

    @Test
    public void createsNullWhenPathIsFilteredOut()
    {
        final Configuration configuration = new Configuration().withDefaultProviders()
                                                               .withPathFilter((path) -> !path.getName().equals("field4"));

        final ObjectBuilder subject = ObjectBuilder.withConfiguration(configuration);

        final MyModel myModel = subject.buildInstanceOf(MyModel.class);
        assertThat(myModel).isNotNull();
        assertThat(myModel.getField4()).isNull();
    }

    @Test
    public void createsDataFromCustomeProvider()
    {
        final MyModel2 myModel2 = new MyModel2();
        final Configuration configuration = new Configuration().withDefaultProviders()
                                                               .withProvider(new Provider()
                                                               {
                                                                   @Override
                                                                   public boolean supports(final Class<?> clazz)
                                                                   {
                                                                       return clazz == MyModel2.class;
                                                                   }

                                                                   @Override
                                                                   public Object provide(final ProviderContext context)
                                                                   {
                                                                       return myModel2;
                                                                   }
                                                               });

        final ObjectBuilder subject = ObjectBuilder.withConfiguration(configuration);

        assertThat(subject.buildInstanceOf(MyModel2.class)).isSameAs(myModel2);
    }
}
