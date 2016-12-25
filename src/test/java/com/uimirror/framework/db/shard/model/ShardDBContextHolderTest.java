package com.uimirror.framework.db.shard.model;

import net.jodah.concurrentunit.Waiter;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Jayaram on 5/30/16.
 */
public class ShardDBContextHolderTest {

    Waiter waiter = new Waiter();

    @Test
    public void testShardDBContextHolder() throws Exception {

        Thread workflow1 = new Thread(new WorkFlow1());
        Thread workflow2 = new Thread(new WorkFlow2());
        Thread workflow3 = new Thread(new WorkFlow3());

        workflow1.start();
        workflow2.start();
        workflow3.start();

        // Wait for the 3 workflows to finish
        waiter.await(1000, 3);

    }

    private class WorkFlow1 implements Runnable {
        public void run() {
            ShardDbContext dbContext1 = new DefaultShardDbContext.Builder("testDB1").withDbType(DBShardMeta.DBType.MASTER).build();
            ShardDbContextHolder.addCurrentDbContext(dbContext1);
            assertThat(ShardDbContextHolder.isInSingleState()).isTrue();
            assertThat(ShardDbContextHolder.getCurrentDbContext()).isEqualTo(dbContext1);

            // workflow1 complete
            waiter.resume();
        }
    }

    private class WorkFlow2 implements Runnable {
        public void run() {
            ShardDbContext dbContext1 = new DefaultShardDbContext.Builder("testDB1").withDbType(DBShardMeta.DBType.SLAVE).build();
            ShardDbContextHolder.addCurrentDbContext(dbContext1);
            ShardDbContext dbContext2 = new DefaultShardDbContext.Builder("testDB1").withDbType(DBShardMeta.DBType.MASTER).build();
            ShardDbContextHolder.addCurrentDbContext(dbContext2);

            assertThat(ShardDbContextHolder.getCurrentDbContext()).isEqualTo(dbContext2);
            assertThat((ShardDbContextHolder.isInSingleState())).isFalse();

            ShardDbContextHolder.removeCurrentDbContext();

            assertThat(ShardDbContextHolder.isInSingleState()).isTrue();
            assertThat(ShardDbContextHolder.getCurrentDbContext()).isEqualTo(dbContext1);

            ShardDbContextHolder.removeCurrentDbContext();
            assertThat(ShardDbContextHolder.getCurrentDbContext()).isNull();

            // workflow2 complete
            waiter.resume();

        }
    }

    private class WorkFlow3 implements Runnable {
        public void run() {
            ShardDbContext dbContext1 = new DefaultShardDbContext.Builder("testDB1").withDbType(DBShardMeta.DBType.SLAVE).build();
            ShardDbContextHolder.addCurrentDbContext(dbContext1);
            ShardDbContext dbContext2 = new DefaultShardDbContext.Builder("testDB1").withDbType(DBShardMeta.DBType.MASTER).build();
            ShardDbContextHolder.addCurrentDbContext(dbContext2);

            assertThat(ShardDbContextHolder.getCurrentDbContext()).isEqualTo(dbContext2);
            ShardDbContextHolder.clearDbContext();

            assertThat(ShardDbContextHolder.getCurrentDbContext()).isNull();

            // workflow3 complete
            waiter.resume();

        }
    }
}
