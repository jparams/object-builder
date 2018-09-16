package com.jparams.object.builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import com.jparams.object.builder.model.MyEmptyEnum;
import com.jparams.object.builder.model.MyEnum;
import com.jparams.object.builder.model.MyInterface;
import com.jparams.object.builder.model.MyModel;
import com.jparams.object.builder.model.MyModel2;
import com.jparams.object.builder.model.MyModel3;
import com.jparams.object.builder.provider.Provider;
import com.jparams.object.builder.type.Type;
import com.jparams.object.builder.type.TypeReference;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ObjectBuilderTest
{
    private ObjectBuilder subject;

    @Before
    public void setUp()
    {
        subject = ObjectBuilder.withDefaultConfiguration();
    }

    @Test
    public void createsArray()
    {
        final String[] strings = subject.buildInstanceOf(String[].class).get();
        assertThat(strings).isNotEmpty();
        assertThat(strings).doesNotContainNull();
    }

    @Test
    public void createsBigDecimal()
    {
        final BigDecimal value = subject.buildInstanceOf(BigDecimal.class).get();
        assertThat(value).isNotNull();
    }

    @Test
    public void createsBoolean()
    {
        final Boolean value = subject.buildInstanceOf(Boolean.class).get();
        assertThat(value).isNotNull();
    }

    @Test
    public void createsBooleanPrimitive()
    {
        final boolean value = subject.buildInstanceOf(boolean.class).get();
        assertThat(value).isNotNull();
    }

    @Test
    public void createsSortedSet()
    {
        final Build<SortedSet<String>> builder = subject.buildInstanceOf(new TypeReference<SortedSet<String>>()
        {
        });

        final SortedSet<String> value = builder.get();

        assertThat(value).isNotNull();
    }

    @Test
    public void createsByte()
    {
        final Byte value = subject.buildInstanceOf(Byte.class).get();
        assertThat(value).isNotNull();
    }

    @Test
    public void createsBytePrimitive()
    {
        final byte value = subject.buildInstanceOf(byte.class).get();
        assertThat(value).isNotNull();
    }

    @Test
    public void createsChar()
    {
        final Character value = subject.buildInstanceOf(Character.class).get();
        assertThat(value).isNotNull();
    }

    @Test
    public void createsCharPrimitive()
    {
        final char value = subject.buildInstanceOf(char.class).get();
        assertThat(value).isNotNull();
    }

    @Test
    public void createsDate()
    {
        final Date value = subject.buildInstanceOf(Date.class).get();
        assertThat(value).isNotNull();
    }

    @Test
    public void createsDouble()
    {
        final Double value = subject.buildInstanceOf(Double.class).get();
        assertThat(value).isNotNull();
    }

    @Test
    public void createsDoublePrimitive()
    {
        final double value = subject.buildInstanceOf(double.class).get();
        assertThat(value).isNotNull();
    }

    @Test
    public void createsFloat()
    {
        final Float value = subject.buildInstanceOf(Float.class).get();
        assertThat(value).isNotNull();
    }

    @Test
    public void createsFloatPrimitive()
    {
        final float value = subject.buildInstanceOf(float.class).get();
        assertThat(value).isNotNull();
    }

    @Test
    public void createsInteger()
    {
        final Integer value = subject.buildInstanceOf(Integer.class).get();
        assertThat(value).isNotNull();
    }

    @Test
    public void createsIntegerPrimitive()
    {
        final int value = subject.buildInstanceOf(int.class).get();
        assertThat(value).isNotNull();
    }

    @Test
    public void createsInterfaceProxy()
    {
        final MyInterface value = subject.buildInstanceOf(MyInterface.class).get();

        assertThat(value).isNotNull();
        assertThat(value.toString()).isNotNull();
        assertThat(value.generateString()).isNotNull();

        value.voidMethod();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void createsEmptyListWhenGenericsNotKnown()
    {
        final List<?> values = subject.buildInstanceOf(List.class).get();
        assertThat(values).isEmpty();
    }

    @Test
    public void createsListWithTypeReference()
    {
        final List<String> values = subject.buildInstanceOf(new TypeReference<List<String>>()
        {
        }).get();

        assertThat(values).isNotEmpty();
        assertThat(values).doesNotContainNull();
    }

    @Test
    public void createsListWithType()
    {
        final Build<List<String>> build = subject.buildInstanceOf(Type.forClass(List.class).withGenerics(String.class));
        final List<String> values = build.get();

        assertThat(values).isNotEmpty();
        assertThat(values).doesNotContainNull();
    }

    @Test
    public void createsListWithDeepGenerics()
    {
        final Build<List<List<BigDecimal>>> build = subject.buildInstanceOf(new TypeReference<List<List<BigDecimal>>>()
        {
        });

        final List<List<BigDecimal>> values = build.get();

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
        final LocalDateTime value = subject.buildInstanceOf(LocalDateTime.class).get();
        assertThat(value).isNotNull();
    }

    @Test
    public void createsLocalDate()
    {
        final LocalDate value = subject.buildInstanceOf(LocalDate.class).get();
        assertThat(value).isNotNull();
    }

    @Test
    public void createsLocalTime()
    {
        final LocalTime value = subject.buildInstanceOf(LocalTime.class).get();
        assertThat(value).isNotNull();
    }

    @Test
    public void createsLong()
    {
        final Long value = subject.buildInstanceOf(Long.class).get();
        assertThat(value).isNotNull();
    }

    @Test
    public void createsLongPrimitive()
    {
        final long value = subject.buildInstanceOf(long.class).get();
        assertThat(value).isNotNull();
    }

    @Test
    public void returnsCachedData()
    {
        final Configuration configuration = Configuration.defaultConfiguration().withCacheAll();
        final ObjectBuilder subject = ObjectBuilder.withConfiguration(configuration);

        final String str1 = subject.buildInstanceOf(String.class).get();
        final String str2 = subject.buildInstanceOf(String.class).get();

        assertThat(str1).isNotNull();
        assertThat(str1).isEqualTo(str2);
    }

    @Test
    public void returnsShallowCopy()
    {
        final Configuration configuration = Configuration.defaultConfiguration().withCachingAllExcluding(Type.forClass(MyModel.class));
        final ObjectBuilder subject = ObjectBuilder.withConfiguration(configuration);

        final MyModel myModel1 = subject.buildInstanceOf(MyModel.class).get();
        final MyModel myModel2 = subject.buildInstanceOf(MyModel.class).get();

        assertThat(myModel1).isNotSameAs(myModel2);
        assertThat(myModel1).isEqualToComparingFieldByFieldRecursively(myModel2);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void createsEmptyMapWhenGenericsNotKnown()
    {
        final Build<Map> build = subject.buildInstanceOf(new TypeReference<Map>()
        {
        });

        assertThat(build.get()).isEmpty();
    }

    @Test
    public void createsMapWithTypeReference()
    {
        final Map<String, BigDecimal> values = subject.buildInstanceOf(new TypeReference<Map<String, BigDecimal>>()
        {
        }).get();

        assertThat(values).isNotEmpty();
    }

    @Test
    public void createsEnum()
    {
        final MyEnum value = subject.buildInstanceOf(MyEnum.class).get();
        assertThat(value).isNotNull();
    }

    @Test
    public void createsNullEnumWhenNoValuesAvailable()
    {
        final MyEmptyEnum value = subject.buildInstanceOf(MyEmptyEnum.class).get();
        assertThat(value).isNull();
    }

    @Test
    public void createsNullValueWhenDepthExhausted()
    {
        final Configuration configuration = Configuration.defaultConfiguration().withMaxDepth(-1);
        final ObjectBuilder subject = ObjectBuilder.withConfiguration(configuration);

        assertThat(subject.buildInstanceOf(String.class).get()).isNull();
        assertThat(subject.buildInstanceOf(byte.class).get()).isEqualTo((byte) 0);
        assertThat(subject.buildInstanceOf(short.class).get()).isEqualTo((short) 0);
        assertThat(subject.buildInstanceOf(int.class).get()).isEqualTo(0);
        assertThat(subject.buildInstanceOf(long.class).get()).isEqualTo((long) 0);
        assertThat(subject.buildInstanceOf(float.class).get()).isEqualTo((float) 0.0);
        assertThat(subject.buildInstanceOf(double.class).get()).isEqualTo((double) 0.0);
        assertThat(subject.buildInstanceOf(boolean.class).get()).isEqualTo(false);
        assertThat(subject.buildInstanceOf(char.class).get()).isEqualTo('\u0000');
    }

    @Test
    public void createsEmptySetWhenGenericsNotKnown()
    {
        final Set<?> values = subject.buildInstanceOf(new TypeReference<Set>()
        {
        }).get();
        assertThat(values).isEmpty();
    }

    @Test
    public void createsShort()
    {
        final Short value = subject.buildInstanceOf(Short.class).get();
        assertThat(value).isNotNull();
    }

    @Test
    public void createsShortPrimitive()
    {
        final short value = subject.buildInstanceOf(short.class).get();
        assertThat(value).isNotNull();
    }

    @Test
    public void createsNullOnException()
    {
        final Configuration configuration = Configuration.defaultConfiguration().withDefaultBuildStrategy(BuildStrategy.CONSTRUCTOR);
        final ObjectBuilder subject = ObjectBuilder.withConfiguration(configuration);
        final MyModel3 value = subject.buildInstanceOf(MyModel3.class).get();
        assertThat(value).isNull();
    }

    @Test
    public void createsNullWhenPathIsFilteredOut()
    {
        final Configuration configuration = Configuration.defaultConfiguration()
                                                         .withPathFilter((path) -> !path.getName().equals("field4"));

        final ObjectBuilder subject = ObjectBuilder.withConfiguration(configuration);

        final MyModel myModel = subject.buildInstanceOf(MyModel.class).get();
        assertThat(myModel).isNotNull();
        assertThat(myModel.getField4()).isNull();
    }

    @Test
    public void createsDataFromCustomProvider()
    {
        final MyModel2 myModel2 = new MyModel2();
        final Configuration configuration = Configuration.defaultConfiguration()
                                                         .withProvider(new Provider()
                                                         {
                                                             @Override
                                                             public boolean supports(final Type<?> type)
                                                             {
                                                                 return type.getJavaType() == MyModel2.class;
                                                             }

                                                             @Override
                                                             public Object provide(final Context context)
                                                             {
                                                                 return myModel2;
                                                             }
                                                         });

        final ObjectBuilder subject = ObjectBuilder.withConfiguration(configuration);

        assertThat(subject.buildInstanceOf(MyModel2.class).get()).isSameAs(myModel2);
    }

    @Test
    public void createsInstanceUsingConstructorInjection()
    {
        final Configuration configuration = Configuration.defaultConfiguration().withDefaultBuildStrategy(BuildStrategy.CONSTRUCTOR);
        final ObjectBuilder subject = ObjectBuilder.withConfiguration(configuration);
        final MyModel actual = subject.buildInstanceOf(MyModel.class).get();
        assertThat(actual).isNotNull();
        assertThat(actual.getField1()).startsWith("--");
    }

    @Test
    public void createsInstanceUsingConstructorInjectionOverridden()
    {
        final Configuration configuration = Configuration.defaultConfiguration().withBuildStrategy(Type.forClass(MyModel.class), BuildStrategy.CONSTRUCTOR).withDefaultBuildStrategy(BuildStrategy.FIELD_INJECTION);
        final ObjectBuilder subject = ObjectBuilder.withConfiguration(configuration);
        final MyModel actual = subject.buildInstanceOf(MyModel.class).get();
        assertThat(actual).isNotNull();
        assertThat(actual.getField1()).startsWith("--");
    }


    @Test
    public void createsInstanceUsingFieldInjection()
    {
        final Configuration configuration = Configuration.defaultConfiguration().withDefaultBuildStrategy(BuildStrategy.FIELD_INJECTION);
        final ObjectBuilder subject = ObjectBuilder.withConfiguration(configuration);
        final MyModel actual = subject.buildInstanceOf(MyModel.class).get();
        assertThat(actual).isNotNull();
        assertThat(actual.getField1()).doesNotStartWith("--");
    }

    @Test
    public void createsInstanceUsingFieldInjectionOverridden()
    {
        final Configuration configuration = Configuration.defaultConfiguration().withBuildStrategy(Type.forClass(MyModel.class), BuildStrategy.FIELD_INJECTION).withDefaultBuildStrategy(BuildStrategy.CONSTRUCTOR);
        final ObjectBuilder subject = ObjectBuilder.withConfiguration(configuration);
        final MyModel actual = subject.buildInstanceOf(MyModel.class).get();
        assertThat(actual).isNotNull();
        assertThat(actual.getField1()).doesNotStartWith("--");
    }

    @Test
    public void returnsPrefabValue()
    {
        final Configuration configuration = Configuration.defaultConfiguration().withPrefabValue(Type.forClass(float.class), 10F);
        final float value = ObjectBuilder.withConfiguration(configuration).buildInstanceOf(float.class).get();
        assertThat(value).isEqualTo(10F);
    }

    @Test
    public void returnsCacheOnlyValue()
    {
        final Configuration configuration = Configuration.defaultConfiguration().withCachingOnly(Type.forClass(MyModel.class));
        final ObjectBuilder objectBuilder = ObjectBuilder.withConfiguration(configuration);
        final MyModel value = objectBuilder.buildInstanceOf(MyModel.class).get();
        assertThat(value).isNotNull();
        assertThat(value).isSameAs(objectBuilder.buildInstanceOf(MyModel.class).get());
        assertThat(value).isSameAs(objectBuilder.buildInstanceOf(MyModel.class).get());
    }

    @Test
    public void returnsCacheExcludingValue()
    {
        final Configuration configuration = Configuration.defaultConfiguration().withCachingAllExcluding(Type.forClass(MyModel.class));
        final ObjectBuilder objectBuilder = ObjectBuilder.withConfiguration(configuration);
        final MyModel value = objectBuilder.buildInstanceOf(MyModel.class).get();

        assertThat(value).isNotNull();

        final MyModel rebuild1 = objectBuilder.buildInstanceOf(MyModel.class).get();
        assertThat(value).isNotSameAs(rebuild1).isEqualToComparingFieldByFieldRecursively(rebuild1);

        final MyModel rebuild2 = objectBuilder.buildInstanceOf(MyModel.class).get();
        assertThat(value).isNotSameAs(rebuild2).isEqualToComparingFieldByFieldRecursively(rebuild2);

        assertThat(rebuild1).isNotSameAs(rebuild2).isEqualToComparingFieldByFieldRecursively(rebuild2);
    }
}
