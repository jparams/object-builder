package com.jparams.object.builder.path;

import com.jparams.object.builder.type.Type;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import static org.assertj.core.api.Assertions.assertThat;

public class PathTest
{
    @Test
    public void testGetDepth()
    {
        assertThat(new Path(null, null, null).getDepth()).isEqualTo(0);
        assertThat(new Path(null, null, new Path(null, null, null)).getDepth()).isEqualTo(1);
    }

    @Test
    public void testGetLocation()
    {
        assertThat(new Path("$", null, null).getLocation()).isEqualTo("$");
        assertThat(new Path("field", null, new Path("$", null, null)).getLocation()).isEqualTo("$.field");
    }

    @Test
    public void testEqualsAndHashCode()
    {
        EqualsVerifier.forClass(Path.class)
                      .usingGetClass()
                      .withPrefabValues(Path.class, new Path("path", Type.forClass(String.class), null), new Path("path1", Type.forClass(String.class), null))
                      .withPrefabValues(Type.class, Type.forClass(String.class), Type.forClass(Integer.class))
                      .verify();
    }
}