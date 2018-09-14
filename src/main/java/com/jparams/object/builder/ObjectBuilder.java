package com.jparams.object.builder;

import com.jparams.object.builder.path.Path;
import com.jparams.object.builder.type.Type;
import com.jparams.object.builder.type.TypeReference;
import com.jparams.object.builder.type.TypeResolver;

/**
 * ObjectBuilder can create an instance of any class using a combination of reflection and pre-defined value providers. This can be used to
 * generate dummy instances of objects. You can use ObjectBuilder as follows:
 *
 * <pre>
 *  ObjectBuilder.withDefaultConfiguration().buildInstanceOf(MyClass.class);
 * </pre>
 */
public class ObjectBuilder
{
    private final ObjectFactory objectFactory;

    private ObjectBuilder(final ObjectFactory objectFactory)
    {
        this.objectFactory = objectFactory;
    }

    /**
     * Create an instance of the given class.
     *
     * @param clazz class
     * @param <T>   type
     * @return build
     */
    public <T> Build<T> buildInstanceOf(final Class<T> clazz)
    {
        final Type type = TypeResolver.resolve(clazz);
        final Path path = new Path("$", type, null);
        return objectFactory.create(path);
    }

    /**
     * Create an instance of the given type reference. Where this method differs from {@link ObjectBuilder#buildInstanceOf(Class)} is that this
     * supports creation of an object with generics. Example:
     *
     * <pre>
     *  ObjectBuilder.withDefaultConfiguration().buildInstanceOf(new TypeReference&lt;List&lt;String&gt;&gt;() {});
     * </pre>
     *
     * @param typeReference type reference
     * @param <T>           type
     * @return build
     */
    public <T> Build<T> buildInstanceOf(final TypeReference<T> typeReference)
    {
        if (typeReference == null || typeReference.getPath() == null)
        {
            return null;
        }

        return objectFactory.create(typeReference.getPath());
    }

    /**
     * Create an instance of the given type reference. Where this method differs from {@link ObjectBuilder#buildInstanceOf(Class)} is that this
     * supports creation of an object with generics. Example:
     *
     * <pre>
     *  ObjectBuilder.withDefaultConfiguration().buildInstanceOf(Type.forClass(List.class).withGenerics(String.class).build());
     * </pre>
     *
     * @param type type
     * @param <T>  type
     * @return build
     */
    public <T> Build<T> buildInstanceOf(final Type type)
    {
        if (type == null)
        {
            return null;
        }

        final Path path = new Path("$", type, null);
        return objectFactory.create(path);
    }

    /**
     * Create an instance of ObjectBuilder with default configuration. This is the same as calling:
     *
     * <pre>
     *  ObjectBuilder.withConfiguration(Configuration.defaultConfiguration());
     * </pre>
     *
     * @return builder
     */
    public static ObjectBuilder withDefaultConfiguration()
    {
        return withConfiguration(Configuration.defaultConfiguration());
    }

    /**
     * Create an instance of ObjectBuilder with the given configuration.
     *
     * @param configuration configuration
     * @return builder
     */
    public static ObjectBuilder withConfiguration(final Configuration configuration)
    {
        return new ObjectBuilder(configuration.buildObjectFactory());
    }
}
