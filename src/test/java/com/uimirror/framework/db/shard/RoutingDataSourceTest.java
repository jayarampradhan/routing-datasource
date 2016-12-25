package com.uimirror.framework.db.shard;


import com.uimirror.framework.db.shard.model.DBShardMeta;
import com.uimirror.framework.db.shard.model.DefaultShardDbContext;
import com.uimirror.framework.db.shard.model.ShardDbContext;
import com.uimirror.framework.db.shard.model.ShardDbContextHolder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Jayaram on 5/10/16.
 */
public class RoutingDataSourceTest {

    private RoutingDataSource rds;

    private static Method testMethod;

    DefaultShardDbContext sbContext;

    @Before
    public void setUpTest() {
        rds = new RoutingDataSource();
    }

    @BeforeClass
    public static void setUp() throws Exception {
        testMethod = ReflectionUtils.findMethod(RoutingDataSource.class, "determineCurrentLookupKey");
        testMethod.setAccessible(true);
    }

    @Test
    public void testDetermineCurrentLookupKeyWithMasterSlave() throws Exception {
        ShardDbContext sbContext = new DefaultShardDbContext.Builder("testDB").withDbType(DBShardMeta.DBType.MASTER).build();
        ShardDbContextHolder.addCurrentDbContext(sbContext);
        Object lookupKey = testMethod.invoke(rds);

        assertThat(lookupKey).isEqualTo(DBShardMeta.DBType.MASTER);
    }

    @Test
    public void testDetermineCurrentLookupKeyWithSeqNum() throws Exception {
        ShardDbContext sbContext = new DefaultShardDbContext.Builder("testDB").withSequenceNum(2).build();
        ShardDbContextHolder.addCurrentDbContext(sbContext);
        Object lookupKey = testMethod.invoke(rds);

        assertThat(lookupKey).isEqualTo(2);
    }

    @Test
    public void testDetermineCurrentLookupKeyWithNull() throws Exception {
        ShardDbContext sbContext = new DefaultShardDbContext.Builder("testDB").build();
        ShardDbContextHolder.addCurrentDbContext(sbContext);

        assertThat(testMethod.invoke(rds)).isNull();
    }
}
