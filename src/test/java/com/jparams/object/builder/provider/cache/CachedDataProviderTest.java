package com.jparams.object.builder.provider.cache;

import java.util.Arrays;

import com.jparams.object.builder.provider.Provider;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class CachedDataProviderTest
{
    private CachedDataProvider subject;
    private Provider mockProvider1;
    private Provider mockProvider2;

    @Before
    public void setUp()
    {
        mockProvider1 = mock(Provider.class);
        mockProvider2 = mock(Provider.class);
        subject = new CachedDataProvider(Arrays.asList(mockProvider1, mockProvider2), 1);
    }

    @Test
    public void executeMatchingProvider()
    {
        //        final ProviderContext context = createContext(0);
        //        subject.provide(context);
    }

    @Test
    public void executeMatchingProviderOnceAndCache()
    {

    }

    //
    //    private ProviderContext createContext(final int parents)
    //    {
    //        return new ProviderContext(createPath(null, parents), null);
    //    }
    //
    //    private Path createPath(final Path parent, final int i)
    //    {
    //        final Path path = new Path("str", String.class, null, parent);
    //
    //        if (i <= 0)
    //        {
    //            return path;
    //        }
    //        else
    //        {
    //            createPath(parent, i - 1);
    //        }
    //    }
}