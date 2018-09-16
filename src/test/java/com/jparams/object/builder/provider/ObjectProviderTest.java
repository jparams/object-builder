package com.jparams.object.builder.provider;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.jparams.object.builder.BuildStrategy;
import com.jparams.object.builder.Context;
import com.jparams.object.builder.model.MyAbstractModel;
import com.jparams.object.builder.model.MyEnum;
import com.jparams.object.builder.model.MyInterface;
import com.jparams.object.builder.model.MyModel;
import com.jparams.object.builder.model.MyModel2;
import com.jparams.object.builder.path.Path;
import com.jparams.object.builder.type.Type;
import com.jparams.object.builder.type.TypeMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ObjectProviderTest
{
    private ObjectProvider subject;

    @Mock
    private TypeMap<BuildStrategy> mockTypeMap;

    @Before
    public void setUp()
    {
        subject = new ObjectProvider(BuildStrategy.AUTO, mockTypeMap);
    }

    @Test
    public void testSupports()
    {
        assertThat(subject.supports(Type.forClass(MyModel.class))).isTrue();
        assertThat(subject.supports(Type.forClass(int.class))).isFalse();
        assertThat(subject.supports(Type.forClass(MyEnum.class))).isFalse();
        assertThat(subject.supports(Type.forClass(MyInterface.class))).isFalse();
        assertThat(subject.supports(Type.forClass(MyAbstractModel.class))).isFalse();
    }

    @Test
    public void testProvideUsingConstructorInjection()
    {
        when(mockTypeMap.findMatch(Type.forClass(MyModel.class))).thenReturn(Optional.of(BuildStrategy.CONSTRUCTOR));

        final Context mockContext = mock(Context.class);
        when(mockContext.getPath()).thenReturn(new Path("", Type.forClass(MyModel.class), null));

        final String field1 = "abc";
        when(mockContext.createChild(any(), eq(Type.forClass(String.class)))).thenReturn(field1);

        final List<Object> field2 = Collections.emptyList();
        when(mockContext.createChild(any(), eq(Type.forClass(List.class).withGenerics(MyModel2.class)))).thenReturn(field2);

        final Set<BigDecimal> field3 = Collections.singleton(BigDecimal.ONE);
        when(mockContext.createChild(any(), eq(Type.forClass(Set.class).withGenerics(BigDecimal.class)))).thenReturn(field3);

        final MyModel provided = (MyModel) subject.provide(mockContext);
        assertThat(provided.getField1()).isEqualTo("--" + field1);
        assertThat(provided.getField2()).isEqualTo(field2);
        assertThat(provided.getField3()).isEqualTo(field3);
        assertThat(provided.getField4()).isNull();
    }

    @Test
    public void testProvideUsingFieldInjection()
    {
        when(mockTypeMap.findMatch(Type.forClass(MyModel.class))).thenReturn(Optional.of(BuildStrategy.FIELD_INJECTION));

        final Context mockContext = mock(Context.class);
        when(mockContext.getPath()).thenReturn(new Path("", Type.forClass(MyModel.class), null));

        final String field1 = "abc";
        when(mockContext.createChild(any(), eq(Type.forClass(String.class)))).thenReturn(field1);

        final List<Object> field2 = Collections.emptyList();
        when(mockContext.createChild(any(), eq(Type.forClass(List.class).withGenerics(MyModel2.class)))).thenReturn(field2);

        final Set<BigDecimal> field3 = Collections.singleton(BigDecimal.ONE);
        when(mockContext.createChild(any(), eq(Type.forClass(Set.class).withGenerics(BigDecimal.class)))).thenReturn(field3);

        final MyModel field4 = mock(MyModel.class);
        when(mockContext.createChild(any(), eq(Type.forClass(MyModel.class)))).thenReturn(field4);

        final MyModel provided = (MyModel) subject.provide(mockContext);
        assertThat(provided.getField1()).isEqualTo(field1);
        assertThat(provided.getField2()).isEqualTo(field2);
        assertThat(provided.getField3()).isEqualTo(field3);
        assertThat(provided.getField4()).isEqualTo(field4);
    }

    @Test
    public void testProvideUsingAuto()
    {
        when(mockTypeMap.findMatch(Type.forClass(MyModel.class))).thenReturn(Optional.empty());

        final Context mockContext = mock(Context.class);
        when(mockContext.getPath()).thenReturn(new Path("", Type.forClass(MyModel.class), null));

        final String field1 = "abc";
        when(mockContext.createChild(any(), eq(Type.forClass(String.class)))).thenReturn(field1);

        final List<Object> field2 = Collections.emptyList();
        when(mockContext.createChild(any(), eq(Type.forClass(List.class).withGenerics(MyModel2.class)))).thenReturn(field2);

        final Set<BigDecimal> field3 = Collections.singleton(BigDecimal.ONE);
        when(mockContext.createChild(any(), eq(Type.forClass(Set.class).withGenerics(BigDecimal.class)))).thenReturn(new ArrayList<>(field3)).thenReturn(field3);

        final MyModel field4 = mock(MyModel.class);
        when(mockContext.createChild(any(), eq(Type.forClass(MyModel.class)))).thenReturn(field4);

        final MyModel provided = (MyModel) subject.provide(mockContext);
        assertThat(provided.getField1()).isEqualTo(field1);
        assertThat(provided.getField2()).isEqualTo(field2);
        assertThat(provided.getField3()).isEqualTo(field3);
        assertThat(provided.getField4()).isEqualTo(field4);
    }
}