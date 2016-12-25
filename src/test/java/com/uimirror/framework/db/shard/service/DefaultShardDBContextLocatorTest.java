package com.uimirror.framework.db.shard.service;

import com.uimirror.framework.db.shard.model.ShardDbContext;
import com.uimirror.framework.db.shard.model.UserContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by Jayaram on 5/30/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultShardDBContextLocatorTest {

    @Mock
    ProceedingJoinPoint pjp;

    DefaultShardDbContextLocator dsContextLocator;

    @Before
    public void setUpTest() {
        dsContextLocator = new DefaultShardDbContextLocator();
    }

    @Test
    public void testLookUpContextValidUser() throws Exception {
        when(pjp.getArgs()).thenReturn(new Object[] {new UserContext.ContextBuilder(123L).usingShard(25).build()});
        ShardDbContext sdc = dsContextLocator.lookUpContext(pjp, "userShard");

        assertThat(sdc.name().equals("userShard"));
        assertThat(sdc.dbSeqNum().equals(25));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLookUpContextNoUserContext() throws Exception {
        when(pjp.getArgs()).thenReturn(new Object[] {"test"});
        ShardDbContext sdc = dsContextLocator.lookUpContext(pjp, "userShard");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLookUpContextNoPJP() throws Exception {
        ShardDbContext sdc = dsContextLocator.lookUpContext(null, "userShard");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLookUpContextNoArgs() throws Exception {
        when(pjp.getArgs()).thenReturn(null);
        ShardDbContext sdc = dsContextLocator.lookUpContext(null, "userShard");
    }

}
