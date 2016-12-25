package com.uimirror.framework.db.shard;

import com.uimirror.framework.db.shard.annotation.ShardConnection;
import com.uimirror.framework.db.shard.model.DBDetailsMeta;
import com.uimirror.framework.db.shard.model.DBShardMeta;
import com.uimirror.framework.db.shard.model.ShardDbContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;

/**
 * Created by Jayaram on 5/17/16.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ShardDbContextHolder.class)
public class ShardedConnectionInterceptorTest {

    ShardedConnectionInterceptor sci;

    @Mock
    ProceedingJoinPoint pjp;

    @Mock
    ShardConnection shardConnection;

    @Mock
    ShardDbContextHolder shardDbContextHolder;

    @Before
    public void setUpTest() throws Throwable {
        sci = new ShardedConnectionInterceptor();

        // Mock the static methods of ShardDbContextHolder
        PowerMockito.mockStatic(ShardDbContextHolder.class);

        PowerMockito.doNothing().when(ShardDbContextHolder.class);
        ShardDbContextHolder.clearDbContext();

        PowerMockito.doNothing().when(ShardDbContextHolder.class);
        ShardDbContextHolder.removeCurrentDbContext();

    }

    @BeforeClass
    public static void setUp() throws Exception {
    }

    @Test
    public void testProceedMasterDB() throws Throwable {

        // doReturn(new Object()).when(pjp).proceed();
        doReturn("test").when(shardConnection).value();
        doReturn(DBDetailsMeta.ShardType.MASTER_SLAVE).when(shardConnection).shardType();
        doReturn(DBShardMeta.DBType.MASTER).when(shardConnection).use();

        Mockito.when(ShardDbContextHolder.isInSingleState()).thenReturn(false);


        Object result = sci.proceed(pjp, shardConnection);

        PowerMockito.verifyStatic(Mockito.times(1));
        ShardDbContextHolder.addCurrentDbContext(any());

        PowerMockito.verifyStatic(Mockito.times(1));
        ShardDbContextHolder.removeCurrentDbContext();
    }
}
